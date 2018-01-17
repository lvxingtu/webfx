package naga.framework.ui.controls;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import naga.framework.activity.view.ViewActivityContextMixin;
import naga.framework.expression.Expression;
import naga.framework.expression.terms.ExpressionArray;
import naga.framework.orm.domainmodel.DataSourceModel;
import naga.framework.orm.domainmodel.DomainClass;
import naga.framework.orm.entity.Entity;
import naga.framework.orm.entity.EntityStore;
import naga.framework.ui.filter.ExpressionColumn;
import naga.framework.ui.filter.ReactiveExpressionFilter;
import naga.framework.ui.filter.StringFilter;
import naga.framework.ui.filter.StringFilterBuilder;
import naga.framework.ui.layouts.SceneUtil;
import naga.fx.properties.Properties;
import naga.fx.spi.Toolkit;
import naga.fxdata.cell.renderer.ValueRenderer;
import naga.fxdata.cell.renderer.ValueRendererFactory;
import naga.fxdata.control.DataGrid;
import naga.fxdata.displaydata.DisplayResultSet;
import naga.util.Arrays;
import naga.util.Strings;
import naga.util.collection.Collections;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static naga.framework.ui.layouts.LayoutUtil.*;

/**
 * @author Bruno Salmon
 */
public class EntityButtonSelector<E extends Entity> {

    public enum ShowMode {
        MODAL_DIALOG,
        DROP_DOWN,
        DROP_UP,
        AUTO
    }

    private Object jsonOrClass;
    private final ViewActivityContextMixin viewActivityContextMixin;
    private final Pane parent;

    private final DataSourceModel dataSourceModel;

    private Button entityButton;
    private Expression renderingExpression;
    private ValueRenderer entityRenderer;
    private final Property<E> entityProperty = new SimpleObjectProperty<>();

    private final Property<ShowMode> showModeProperty = new SimpleObjectProperty<>(ShowMode.AUTO);
    private EntityStore loadingStore;
    private BorderPane entityDialogPane;
    private final DoubleProperty entityDialogPaneHeightProperty = new SimpleDoubleProperty();
    private Button modalButton;
    private TextField searchTextField;
    private HBox searchBox;
    private Button okButton, cancelButton;
    private HBox buttonBar;
    private DialogCallback entityDialogCallback;
    private ReactiveExpressionFilter<E> entityDialogFilter;
    // Good to put a limit especially for low-end mobiles
    private int adaptiveLimit = 6; // starting with 6 entries (fit with drop down/up) but can be increased in modal in dependence of the available height

    public EntityButtonSelector(Object jsonOrClass, ViewActivityContextMixin viewActivityContextMixin, Pane parent, DataSourceModel dataSourceModel) {
        this.viewActivityContextMixin = viewActivityContextMixin;
        this.parent = parent;
        this.dataSourceModel = dataSourceModel;
        setJsonOrClass(jsonOrClass);
        Properties.runOnPropertiesChange(p -> updateEntityButton(), entityProperty);
    }

    public ShowMode getShowMode() {
        return showModeProperty.getValue();
    }

    public Property<ShowMode> showModeProperty() {
        return showModeProperty;
    }

    public void setShowMode(ShowMode showModeProperty) {
        this.showModeProperty.setValue(showModeProperty);
    }

    public void setJsonOrClass(Object jsonOrClass) {
        this.jsonOrClass = jsonOrClass;
        renderingExpression = null;
        if (jsonOrClass != null) {
            StringFilter stringFilter = new StringFilterBuilder(jsonOrClass).build();
            DomainClass entityClass = dataSourceModel.getDomainModel().getClass(stringFilter.getDomainClassId());
            if (stringFilter.getColumns() != null) {
                ExpressionColumn[] expressionColumns = ExpressionColumn.fromJsonArray(stringFilter.getColumns());
                renderingExpression = new ExpressionArray(Arrays.map(expressionColumns, expressionColumn -> expressionColumn.parseExpressionDefinitionIfNecessary(dataSourceModel.getDomainModel(), stringFilter.getDomainClassId()).getExpression(), Expression[]::new));
            } else if (stringFilter.getFields() != null)
                renderingExpression = entityClass.parseExpression(stringFilter.getFields());
            else
                renderingExpression = entityClass.getForeignFields();
        }
        entityRenderer = renderingExpression == null ? null : ValueRendererFactory.getDefault().createCellRenderer(renderingExpression.getType());
        entityDialogPane = null;
    }

    public void setEditable(boolean editable) {
        getEntityButton().setDisable(!editable);
    }

    public void setLoadingStore(EntityStore loadingStore) {
        this.loadingStore = loadingStore;
    }

    public Property<E> entityProperty() {
        return entityProperty;
    }

    public E getEntity() {
        return entityProperty.getValue();
    }

    public void setEntity(E entity) {
        entityProperty.setValue(entity);
    }

    public Button getEntityButton() {
        if (entityButton == null)
            setEntityButton(ButtonUtil.newDropDownButton());
        return entityButton;
    }

    public void setEntityButton(Button entityButton) {
        this.entityButton = entityButton;
        entityButton.setOnAction(e -> showEntityDialog());
    }

    private void updateEntityButton() {
        Toolkit.get().scheduler().runInUiThread(() -> {
            E entity = getEntity();
            Object renderedValue = entity == null ? null : entity.evaluate(renderingExpression);
            Node renderedNode = entityRenderer.renderCellValue(renderedValue);
            getEntityButton().setGraphic(renderedNode);
        });
    }

    public void showEntityDialog() {
        setUpEntityDialog(true);
    }

    private DataGrid dataGrid;
    private final Property<DisplayResultSet> deferredDisplayResultSet = new SimpleObjectProperty<>();

    private void setUpEntityDialog(boolean show) {
        if (entityDialogPane == null) {
            if (entityRenderer == null)
                return;
            if (dataGrid == null) {
                dataGrid = new DataGrid();
                dataGrid.setHeaderVisible(false);
                dataGrid.setFullHeight(true);
                BorderPane.setAlignment(dataGrid, Pos.TOP_LEFT);
                ViewActivityContextMixin mixin = viewActivityContextMixin;
                EntityStore filterStore = loadingStore != null ? loadingStore : getEntity() != null ? getEntity().getStore() : null;
                entityDialogFilter = new ReactiveExpressionFilter<E>(jsonOrClass).setDataSourceModel(dataSourceModel).setI18n(mixin).setStore(filterStore);
                String searchCondition = entityDialogFilter.getDomainClass().getSearchCondition();
                if (searchCondition != null) {
                    searchTextField = mixin.newTextFieldWithPrompt("GenericSearchPlaceholder");
                    entityDialogFilter.combine(searchTextField.textProperty(), s -> {
                        if (Strings.isEmpty(s))
                            return null;
                        setSearchParameters(s, entityDialogFilter.getStore());
                        return "{where: `" + searchCondition + "`}";
                    });
                    searchTextField.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                        if (KeyCode.ESCAPE.equals(e.getCode()) || e.getCharacter().charAt(0) == 27) {
                            entityDialogCallback.closeDialog();
                            e.consume();
                        }
                    });
                    HBox.setHgrow(searchTextField, Priority.ALWAYS);
                    modalButton = new Button("...");
                    modalButton.setOnAction(e -> {
                        entityDialogCallback.closeDialog();
                        entityDialogPane = null; setUpEntityDialog(false); // This line could be removed but
                        show(ShowMode.MODAL_DIALOG);
                    });
                }
                entityDialogFilter
                        .combine(entityDialogPaneHeightProperty, height -> "{limit: " + updateAdaptiveLimit(height) + "}")
                        .setExpressionColumns(ExpressionColumn.create(renderingExpression))
                        .displayResultSetInto(dataGrid.displayResultSetProperty())
                        .setDisplaySelectionProperty(dataGrid.displaySelectionProperty())
                        //.setSelectedEntityHandler(dataGrid.displaySelectionProperty(), o -> onOkEntityDialog())
                        .start();
                dataGrid.setOnMouseClicked(e -> {if (e.getClickCount() == 1) onOkEntityDialog(); });
                dataGrid.displayResultSetProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> deferredDisplayResultSet.setValue(newValue)));
            }
            entityDialogPane = new BorderPane(dataGrid);
            entityDialogPane.setBorder(BorderUtil.newBorder(Color.DARKGRAY));
            entityDialogPaneHeightProperty.bind(entityDialogPane.heightProperty());
        }
        entityDialogFilter.setActive(true);
        if (show) {
            entityDialogPane.setPadding(Insets.EMPTY);
            show(computeDecidedShowMode());
        }
    }

    private ShowMode computeDecidedShowMode() {
        ShowMode decidedShowMode = getShowMode();
        if (decidedShowMode == ShowMode.AUTO) {
            Point2D buttonBottom = entityButton.localToScene(0, entityButton.getHeight());
            decidedShowMode = entityButton.getScene().getHeight() - buttonBottom.getY() < 200d + searchTextField.getHeight() ? ShowMode.DROP_UP : ShowMode.DROP_DOWN;
        }
        return decidedShowMode;
    }

    private void show(ShowMode decidedShowMode) {
        if (entityDialogCallback != null)
            entityDialogCallback.closeDialog();
        switch (decidedShowMode) {
            case MODAL_DIALOG:
                setMaxPrefSizeToInfinite(dataGrid);
                if (buttonBar == null) {
                    okButton = viewActivityContextMixin.newOkButton(this::onOkEntityDialog);
                    cancelButton = viewActivityContextMixin.newCancelButton(this::onCancelEntityDialog);
                    buttonBar = new HBox(20, createHGrowable(), okButton, cancelButton, createHGrowable());
                    buttonBar.setPadding(new Insets(20, 0, 0, 0));
                }
                entityDialogPane.setTop(searchTextField);
                entityDialogPane.setBottom(buttonBar);
                entityDialogCallback = DialogUtil.showModalNodeInGoldLayout(entityDialogPane, parent, 0.9, 0.8);
                // Resetting default and cancel buttons (required for JavaFx if displayed a second time)
                ButtonUtil.resetDefaultButton(okButton);
                ButtonUtil.resetCancelButton(cancelButton);
                break;

            case DROP_DOWN:
            case DROP_UP:
                setMaxPrefSize(dataGrid, USE_COMPUTED_SIZE);
                dataGrid.setMaxHeight(200d);
                searchBox = new HBox(searchTextField, modalButton);
                onDecidedShowMode(decidedShowMode);
                entityDialogCallback = DialogUtil.showDropUpOrDownDialog(entityDialogPane, entityButton, parent, deferredDisplayResultSet, decidedShowMode == ShowMode.DROP_UP);
                ChangeListener<Number> sceneHeightListener = (observable, oldValue, newValue) -> Platform.runLater(() -> {
                    SceneUtil.scrollNodeToBeVerticallyVisibleOnScene(entityButton, true, true);
                    onDecidedShowMode(computeDecidedShowMode()); // decided show mode may change in dependence of the height
                    DialogUtil.updateDropUpOrDownDialogPosition(entityDialogPane);
                });
                ObservableValue<? extends Number> sceneHeightProperty = entityDialogPane.getScene().heightProperty();
                sceneHeightProperty.addListener(sceneHeightListener);
                entityDialogCallback.addCloseHook(() -> sceneHeightProperty.removeListener(sceneHeightListener));
                break;
        }
        if (searchTextField != null) {
            searchTextField.setText(null); // Resetting the search box
            SceneUtil.autoFocusIfEnabled(searchTextField);
        }
    }

    private void onDecidedShowMode(ShowMode decidedShowMode) {
        boolean focused = searchTextField.isFocused();
        if (decidedShowMode == ShowMode.DROP_DOWN) {
            entityDialogPane.setBottom(null);
            entityDialogPane.setTop(searchBox);
        } else {
            entityDialogPane.setTop(null);
            entityDialogPane.setBottom(searchBox);
        }
        if (focused)
            searchTextField.requestFocus();
        DialogUtil.setDropDialogUp(entityDialogPane, decidedShowMode == ShowMode.DROP_UP);
    }

    private int updateAdaptiveLimit(Number height) {
        int maxNumberOfVisibleEntries = height.intValue() / 36;
        if (maxNumberOfVisibleEntries > adaptiveLimit)
            adaptiveLimit = maxNumberOfVisibleEntries + 6; // extra 6 to avoid repetitive requests when resizing window
        return adaptiveLimit;
    }

    protected void setSearchParameters(String search, EntityStore store) {
        store.setParameterValue("search", search);
        store.setParameterValue("lowerSearch", search.toLowerCase());
        store.setParameterValue("searchLike", "%" + search + "%");
        store.setParameterValue("lowerSearchLike", "%" + search.toLowerCase() + "%");
    }

    private void onOkEntityDialog() {
        closeEntityDialog();
        setEntity(entityDialogFilter.getSelectedEntity());
    }

    private void onCancelEntityDialog() {
        closeEntityDialog();
    }

    private void closeEntityDialog() {
        entityDialogCallback.closeDialog();
        entityDialogFilter.setActive(false);
        entityDialogCallback = null;
    }

    public void autoSelectFirstEntity() {
        setUpEntityDialog(false);
        if (entityDialogFilter != null)
            entityDialogFilter.setEntitiesHandler(entityList -> setEntity(Collections.first(entityList)));
    }
}
