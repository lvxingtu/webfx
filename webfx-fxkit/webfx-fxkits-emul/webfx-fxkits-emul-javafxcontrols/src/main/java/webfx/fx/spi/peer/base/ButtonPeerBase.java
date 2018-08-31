package webfx.fx.spi.peer.base;

import emul.javafx.scene.control.Button;

/**
 * @author Bruno Salmon
 */
public class ButtonPeerBase
        <N extends Button, NB extends ButtonPeerBase<N, NB, NM>, NM extends ButtonPeerMixin<N, NB, NM>>

        extends ButtonBasePeerBase<N, NB, NM> {
}