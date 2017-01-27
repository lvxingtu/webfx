package mongoose.activities.backend.tester.savetest;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import naga.framework.activity.presentation.view.impl.PresentationViewActivityImpl;

/**
 * @author Bruno Salmon
 */
public class SaveTestPresentationViewActivity extends PresentationViewActivityImpl<SaveTestPresentationModel> {

    private TextField testName;
    private TextField testComment;
    private Button saveTest;

    @Override
    protected void createViewNodes(SaveTestPresentationModel pm) {
        testName = new TextField();
        testComment = new TextField();
        saveTest = new Button();
        testName.setPromptText("Test name");
        testComment.setPromptText("Comments");
        saveTest.setText("Save Test");

        pm.testNameProperty().bind(testName.textProperty());
        pm.testCommentProperty().bind(testComment.textProperty());
        saveTest.onActionProperty().bind(pm.onSaveTestProperty());

        testName.requestFocus();
    }

    @Override
    protected Node assemblyViewNodes() {
        return new BorderPane(null, new VBox(testName, testComment), null, saveTest, null);
    }
}