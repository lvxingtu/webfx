package webfx.tool.buildtool.modulefiles;

import org.w3c.dom.Element;
import webfx.tool.buildtool.Module;
import webfx.tool.buildtool.Platform;
import webfx.tool.buildtool.ProjectModule;
import webfx.tool.buildtool.util.reusablestream.ReusableStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * @author Bruno Salmon
 */
public class JavaModuleFile extends ModuleFile {

    private final ReusableStream<Module> optionalModules;

    public JavaModuleFile(ProjectModule module) {
        super(module);
        optionalModules = module.getWebfxModuleFile().getOptionalModules();
    }

    @Override
    Path getModulePath() {
        return getModule().getJavaSourceDirectory().resolve("module-info.java");
    }

    String getJavaModuleName() {
        return getJavaModuleName(getModule());
    }

    @Override
    void readFile() {
    }

    @Override
    public void writeFile() {
        ProjectModule module = getModule();
        StringBuilder sb = new StringBuilder("// Generated by WebFx\n\nmodule ").append(getJavaModuleName()).append(" {\n");
        processSection(sb, "Direct dependencies modules", "requires",
                module.getDirectDependencies()
                        .map(this::getJavaModuleNameWithStaticPrefixIfApplicable)
                        .distinct()
        );
        processSection(sb, "Resources modules", "requires",
                module.getResourceModules()
                        .map(this::getJavaModuleNameWithStaticPrefixIfApplicable)
                        .distinct()
        );

        processSection(sb, "Exported packages", "exports",
                module.getDeclaredJavaPackages()
        );
        processSection(sb, "Resources packages", "opens",
                module.getResourcePackages()
        );
        processSection(sb, "Used services", "uses",
                module.getUsedJavaServices()
        );
        ReusableStream<String> providedJavaServices = module.getProvidedJavaServices();
        if (module.getTarget().isPlatformSupported(Platform.JRE) && providedJavaServices.count() > 0) {
            sb.append("\n    // Provided services\n");
            providedJavaServices
                    .stream()
                    .sorted()
                    .forEach(s -> sb.append("    provides ").append(s).append(" with ").append(module.getProvidedJavaServiceImplementations(s).collect(Collectors.joining(", "))).append(";\n"));
        }
        sb.append("\n}");

        Path path = getModulePath();
        try {
            //Files.createDirectories(path.getParent()); // Creating all necessary directories
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processSection(StringBuilder sb, String sectionName, String keyword, ReusableStream<String> tokens) {
        if (tokens.count() > 0) {
            sb.append("\n    // ").append(sectionName).append('\n');
            tokens.stream()
                    .sorted()
                    .forEach(p -> sb.append("    ").append(keyword).append(' ').append(p).append(";\n"));
        }
    }

    private String getJavaModuleNameWithStaticPrefixIfApplicable(Module module) {
        String javaModuleName = getJavaModuleName(module);
        if (optionalModules.filter(m -> m == module).findFirst().isPresent())
            return "static " + javaModuleName;
        return javaModuleName;
    }

    private static String getJavaModuleName(Module module) {
        String artifactId = module.getArtifactId();
        switch (artifactId) {
            case "webfx-fxkit-emul-javafxbase":
                return "javafx.base";
            case "webfx-fxkit-emul-javafxcontrols":
                return "javafx.controls";
            case "webfx-fxkit-emul-javafxgraphics":
                return "javafx.graphics";
            default:
                if (module instanceof ProjectModule) {
                    ProjectModule projectModule = (ProjectModule) module;
                    Element documentElement = projectModule.getWebfxModuleFile().getDocument().getDocumentElement();
                    String superModule = documentElement == null ? null : documentElement.getAttribute("super-module");
                    if (superModule != null && !superModule.equals(""))
                        artifactId = projectModule.getRootModule().findModule(superModule).getArtifactId();
                }
                return artifactId.replaceAll("[^a-zA-Z0-9]", ".");
        }
    }
}
