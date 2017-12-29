package mongoose.activities.shared.book.event.shared;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import mongoose.activities.shared.generic.MongooseSectionFactoryMixin;
import mongoose.activities.shared.generic.eventdependent.EventDependentViewDomainActivity;
import mongoose.entities.Event;
import naga.framework.ui.controls.BackgroundUtil;
import naga.framework.ui.layouts.LayoutUtil;

/**
 * @author Bruno Salmon
 */
public abstract class BookingProcessViewActivity
        extends EventDependentViewDomainActivity
        implements MongooseSectionFactoryMixin {

    private final String nextPage;

    protected Button backButton;
    protected Button nextButton;

    protected BorderPane pageContainer;
    protected ScrollPane verticalScrollPane;
    protected VBox verticalStack;

    public BookingProcessViewActivity(String nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public Node buildUi() {
        createViewNodes();
        return styleUi(assemblyViewNodes());
    }

    protected void createViewNodes() {
        if (backButton == null)
            backButton = newTransparentButton("<<Back");
        if (nextButton == null)
            nextButton = newLargeGreenButton( "Next>>");
        backButton.setOnAction(this::onPreviousButtonPressed);
        nextButton.setOnAction(this::onNextButtonPressed);

        pageContainer = new BorderPane(verticalScrollPane = LayoutUtil.createVerticalScrollPaneWithPadding(verticalStack = new VBox(10)));
    }

    protected Node assemblyViewNodes() {
        return pageContainer;
    }

    protected Node styleUi(Node uiNode) {
        if (uiNode instanceof Region)
            onEvent().setHandler(ar -> {
                if (ar.succeeded()) {
                    Event event = ar.result();
                    String css = event.getStringFieldValue("cssClass");
                    if (css != null && css.startsWith("linear-gradient")) {
                        Background eventBackground = BackgroundUtil.newLinearGradientBackground(css);
                        ((Region) uiNode).setBackground(eventBackground);
                    }
                }
            });
        return uiNode;
    }

    private void onPreviousButtonPressed(ActionEvent event) {
        getHistory().goBack();
    }

    protected void onNextButtonPressed(ActionEvent event) {
        goToNextBookingProcessPage(nextPage);
    }

    protected void goToNextBookingProcessPage(String page) {
        getHistory().push("/book/event/" + getEventId() + "/" + page);
    }
}
