package webfx.fxdata.control;

import javafx.scene.Node;
import webfx.fxdata.displaydata.DisplayResult;
import webfx.fxdata.displaydata.DisplaySelection;

/**
 * @author Bruno Salmon
 */
public abstract class SelectableDisplayResultControlSkinBase<C extends SelectableDisplayResultControl, ROW extends Node, CELL extends Node>
        extends DisplayResultControlSkinBase<C, ROW, CELL> {

    public SelectableDisplayResultControlSkinBase(C control, boolean hasSpecialRenderingForImageAndText) {
        super(control, hasSpecialRenderingForImageAndText);
    }

    @Override
    protected void start() {
        super.start();
        updateDisplaySelection(getSkinnable().getDisplaySelection(), null);
        getSkinnable().displaySelectionProperty().addListener((observable, oldValue, newValue) -> updateDisplaySelection(newValue, oldValue));
    }

    private void updateDisplaySelection(DisplaySelection selection, DisplaySelection oldSelection) {
        if (oldSelection != null)
            oldSelection.forEachRow(rowIndex -> getOrAddBodyRow(rowIndex).getStyleClass().remove("selected"));
        if (selection != null)
            selection.forEachRow(rowIndex -> getOrAddBodyRow(rowIndex).getStyleClass().add("selected"));
    }

    @Override
    protected void updateResult(DisplayResult rs) {
        getSkinnable().setDisplaySelection(null);
        super.updateResult(rs);
    }

    @Override
    protected void setUpBodyRow(ROW bodyRow, int rowIndex) {
        super.setUpBodyRow(bodyRow, rowIndex);
        bodyRow.setOnMouseClicked(e -> {
            C control = getSkinnable();
            DisplaySelection displaySelection = control.getDisplaySelection();
            if (displaySelection == null || displaySelection.getSelectedRow() != rowIndex)
                displaySelection = DisplaySelection.createSingleRowSelection(rowIndex);
            else
                displaySelection = null;
            control.setDisplaySelection(displaySelection);
        });
    }
}