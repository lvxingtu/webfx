package webfx.tool.buildtool.sourcegenerators;

import webfx.tool.buildtool.ProjectModule;
import webfx.tool.buildtool.RootModule;
import webfx.tool.buildtool.util.reusablestream.ReusableStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Bruno Salmon
 */
public final class GwtServiceLoaderSuperSourceGenerator {

    private final static String SERVICE_LOADER_JAVA_TEMPLATE = "package java.util;\n" +
            "\n" +
            "import java.util.Iterator;\n" +
            "import java.util.logging.Logger;\n" +
            "import webfx.platform.shared.util.function.Factory;\n" +
            "\n" +
            "public class ServiceLoader<S> implements Iterable<S> {\n" +
            "\n" +
            "    public static <S> ServiceLoader<S> load(Class<S> serviceClass) {\n" +
            "        switch (serviceClass.getName()) {\n" +
            "${generatedCasesCode}" +
            "            // SPI NOT FOUND\n" +
            "            default:\n" +
            "               Logger.getLogger(ServiceLoader.class.getName()).warning(\"SPI not found for \" + serviceClass);\n" +
            "               return new ServiceLoader<S>();\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    private final Factory[] factories;\n" +
            "\n" +
            "    public ServiceLoader(Factory... factories) {\n" +
            "        this.factories = factories;\n" +
            "    }\n" +
            "\n" +
            "    public Iterator<S> iterator() {\n" +
            "        return new Iterator<S>() {\n" +
            "            int index = 0;\n" +
            "            @Override\n" +
            "            public boolean hasNext() {\n" +
            "                return index < factories.length;\n" +
            "            }\n" +
            "\n" +
            "            @Override\n" +
            "            public S next() {\n" +
            "                return (S) factories[index++].create();\n" +
            "            }\n" +
            "        };\n" +
            "    }\n" +
            "}";

    public static void generateServiceLoaderSuperSource(ProjectModule module) {
        log("************************************************************************");
        log("***** Generating java.util.ServiceLoader.java super source for GWT *****");
        log("************************************************************************");
        StringBuilder sb = new StringBuilder();
        sb.append("            // Single SPI providers\n");
        appendServiceLoaderCasesCode(sb, module, true);
        sb.append("            // Multiple SPI providers\n");
        appendServiceLoaderCasesCode(sb, module, false);
        writeJavaTemplateSuperSourceFile(SERVICE_LOADER_JAVA_TEMPLATE, sb, module.getResourcesDirectory().resolve("super/java/util/ServiceLoader.java"));
    }

    private static void writeJavaTemplateSuperSourceFile(String javaTemplate, CharSequence generatedCasesCode, Path path) {
        writeJavaSuperSourceFile(javaTemplate.replace("${generatedCasesCode}", generatedCasesCode), path);
    }

    private static void writeJavaSuperSourceFile(String javaFileContent, Path path) {
        try {
            log(">>> Writing " + path);
            writeTextFile(path, javaFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendServiceLoaderCasesCode(StringBuilder sb, ProjectModule module, boolean single) {
        ProjectModule.filterProjectModules(module.getThisAndTransitiveDependencies())
                .flatMap(m -> single ? m.getUsedRequiredJavaServices() : m.getUsedOptionalJavaServices())
                .forEach(spiClassName -> {
                    ReusableStream<String> providerClassNames = RootModule.findModulesProvidingJavaService(single ? module.getImplementationScope() : ProjectModule.filterProjectModules(module.getThisAndTransitiveDependencies()), spiClassName, module.getTarget(), single)
                            .flatMap(m -> m.getProvidedJavaServiceImplementations(spiClassName));
                    if (providerClassNames.count() == 0)
                        log("WARNING: No provider found for " + spiClassName);
                    else {
                        sb.append("            case \"").append(spiClassName).append("\": return new ServiceLoader<S>(");
                        String firstProvider = providerClassNames.findFirst().get();
                        providerClassNames.forEach(providerClassName -> {
                            if (!providerClassName.equals(firstProvider))
                                if (single) {
                                    log("INFO: Keeping single provider " + firstProvider + ", skipping provider " + providerClassName);
                                    return;
                                } else
                                    sb.append(", ");
                            sb.append(providerClassName.replace('$', '.')).append(providerClassName.equals("webfx.platform.shared.services.json.spi.impl.gwt.GwtJsonObject") ? "::create" : "::new");
                        });
                        sb.append(");\n");
                    }
                });
    }

    private static Path writeTextFile(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent()); // Creating all necessary directories
        //Files.write(path, lzJson.getBytes("UTF-8")); // Writing the file
        BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
        writer.write(content);
        writer.flush();
        writer.close();
        return path;
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
