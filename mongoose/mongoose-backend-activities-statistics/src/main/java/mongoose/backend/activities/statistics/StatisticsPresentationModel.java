package mongoose.backend.activities.statistics;

import javafx.beans.property.*;
import mongoose.client.presentationmodel.HasGroupDisplayResultProperty;
import mongoose.client.presentationmodel.HasGroupDisplaySelectionProperty;
import mongoose.client.presentationmodel.HasSelectedGroupConditionStringFilterProperty;
import mongoose.client.presentationmodel.HasSelectedGroupProperty;
import mongoose.client.activity.eventdependent.EventDependentGenericTablePresentationModel;
import mongoose.client.presentationmodel.HasColumnsStringFilterProperty;
import mongoose.client.presentationmodel.HasConditionStringFilterProperty;
import mongoose.client.presentationmodel.HasGroupStringFilterProperty;
import mongoose.shared.entities.Document;
import mongoose.shared.entities.DocumentLine;
import webfx.fxkit.extra.displaydata.DisplayResult;
import webfx.fxkit.extra.displaydata.DisplaySelection;

/**
 * @author Bruno Salmon
 */
final class StatisticsPresentationModel extends EventDependentGenericTablePresentationModel implements
        HasConditionStringFilterProperty,
        HasGroupDisplayResultProperty,
        HasGroupDisplaySelectionProperty,
        HasGroupStringFilterProperty,
        HasSelectedGroupProperty,
        HasSelectedGroupConditionStringFilterProperty,
        HasColumnsStringFilterProperty {

    private final StringProperty conditionStringFilterProperty = new SimpleStringProperty();
    @Override public final StringProperty conditionStringFilterProperty() { return conditionStringFilterProperty; }

    private final ObjectProperty<DisplayResult> groupDisplayResultProperty = new SimpleObjectProperty<>();
    @Override public ObjectProperty<DisplayResult> groupDisplayResultProperty() { return groupDisplayResultProperty; }

    private final ObjectProperty<DisplaySelection> groupDisplaySelectionProperty = new SimpleObjectProperty<>();
    @Override public ObjectProperty<DisplaySelection> groupDisplaySelectionProperty() { return groupDisplaySelectionProperty; }

    private final StringProperty groupStringFilterProperty = new SimpleStringProperty();
    @Override public final StringProperty groupStringFilterProperty() { return groupStringFilterProperty; }

    private final ObjectProperty<DocumentLine> selectedGroupProperty = new SimpleObjectProperty<>();
    @Override public ObjectProperty<DocumentLine> selectedGroupProperty() {
        return selectedGroupProperty;
    }

    private final StringProperty selectedGroupConditionStringFilterProperty = new SimpleStringProperty();
    @Override public StringProperty selectedGroupConditionStringFilterProperty() { return selectedGroupConditionStringFilterProperty; }

    private final StringProperty columnsStringFilterProperty = new SimpleStringProperty();
    @Override public final StringProperty columnsStringFilterProperty() { return columnsStringFilterProperty; }

    private final ObjectProperty<DocumentLine> selectedDocumentLineProperty = new SimpleObjectProperty<>();
    ObjectProperty<DocumentLine> selectedDocumentLineProperty() {
        return selectedDocumentLineProperty;
    }
    void setSelectedDocumentLine(DocumentLine document) {
        selectedDocumentLineProperty.set(document);
    }

    private final ObjectProperty<Document> selectedDocumentProperty = new SimpleObjectProperty<>();
    ObjectProperty<Document> selectedDocumentProperty() {
        return selectedDocumentProperty;
    }
    void setSelectedDocument(Document document) {
        selectedDocumentProperty.set(document);
    }

}
