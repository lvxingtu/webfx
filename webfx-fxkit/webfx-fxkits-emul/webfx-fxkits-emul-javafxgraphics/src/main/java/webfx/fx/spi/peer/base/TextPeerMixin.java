package webfx.fx.spi.peer.base;

import emul.javafx.geometry.VPos;
import emul.javafx.scene.text.Font;
import emul.javafx.scene.text.Text;
import emul.javafx.scene.text.TextAlignment;

/**
 * @author Bruno Salmon
 */
public interface TextPeerMixin
        <N extends Text, NB extends TextPeerBase<N, NB, NM>, NM extends TextPeerMixin<N, NB, NM>>

        extends ShapePeerMixin<N, NB, NM> {

    void updateText(String text);

    void updateTextOrigin(VPos textOrigin);

    void updateX(Double x);

    void updateY(Double y);

    void updateWrappingWidth(Double wrappingWidth);

    void updateTextAlignment(TextAlignment textAlignment);

    void updateFont(Font font);

}