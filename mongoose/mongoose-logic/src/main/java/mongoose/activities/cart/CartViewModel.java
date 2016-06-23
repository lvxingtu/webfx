package mongoose.activities.cart;

import naga.core.ui.presentation.ViewModel;
import naga.core.spi.toolkit.node.GuiNode;
import naga.core.spi.toolkit.controls.Table;

/**
 * @author Bruno Salmon
 */
class CartViewModel implements ViewModel {

    private final GuiNode contentNode;
    private final Table documentTable;
    private final Table documentLineTable;
    private final Table paymentTable;

    CartViewModel(GuiNode contentNode, Table documentTable, Table documentLineTable, Table paymentTable) {
        this.contentNode = contentNode;
        this.documentTable = documentTable;
        this.documentLineTable = documentLineTable;
        this.paymentTable = paymentTable;
    }

    @Override
    public GuiNode getContentNode() {
        return contentNode;
    }

    Table getDocumentTable() {
        return documentTable;
    }

    Table getDocumentLineTable() {
        return documentLineTable;
    }

    Table getPaymentTable() {
        return paymentTable;
    }
}
