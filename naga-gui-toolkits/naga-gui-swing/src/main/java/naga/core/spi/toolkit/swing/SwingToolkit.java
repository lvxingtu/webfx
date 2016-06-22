package naga.core.spi.toolkit.swing;

import naga.core.spi.toolkit.Toolkit;
import naga.core.spi.toolkit.nodes.*;
import naga.core.spi.toolkit.swing.nodes.*;

import javax.swing.*;

/**
 * @author Bruno Salmon
 */
public class SwingToolkit  extends Toolkit {

    static {
        try {
            UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignored) {}
    }

    public SwingToolkit() {
        super(SwingScheduler.SINGLETON, SwingWindow::new);
        registerNodeFactory(VPage.class, SwingVPage::new);
        registerNodeFactory(Table.class, SwingTable::new);
        registerNodeFactory(CheckBox.class, SwingCheckBox::new);
        registerNodeFactory(ToggleSwitch.class, SwingCheckBox::new);
        registerNodeFactory(SearchBox.class, SwingSearchBox::new);
        registerNodeFactoryAndWrapper(Button.class, SwingButton::new, JButton.class, SwingButton::new);
        registerNodeFactory(VBox.class, SwingVBox::new);
        registerNodeFactory(HBox.class, SwingHBox::new);
    }
}
