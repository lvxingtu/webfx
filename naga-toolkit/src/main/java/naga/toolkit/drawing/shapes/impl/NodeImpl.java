package naga.toolkit.drawing.shapes.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import naga.toolkit.drawing.shapes.BlendMode;
import naga.toolkit.drawing.shapes.Node;
import naga.toolkit.effect.Effect;
import naga.toolkit.spi.events.MouseEvent;
import naga.toolkit.spi.events.UiEventHandler;
import naga.toolkit.transform.Transform;
import naga.toolkit.transform.Translate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Bruno Salmon
 */
class NodeImpl implements Node {

    private final ObjectProperty<UiEventHandler<? super MouseEvent>> onMouseClickedProperty = new SimpleObjectProperty<>();
    @Override
    public ObjectProperty<UiEventHandler<? super MouseEvent>> onMouseClickedProperty() {
        return onMouseClickedProperty;
    }

    private final Property<Boolean> visibleProperty = new SimpleObjectProperty<>(true);
    @Override
    public Property<Boolean> visibleProperty() {
        return visibleProperty;
    }

    private final Property<Double> opacityProperty = new SimpleObjectProperty<>(1d);
    @Override
    public Property<Double> opacityProperty() {
        return opacityProperty;
    }

    private final Property<Node> clipProperty = new SimpleObjectProperty<>();
    @Override
    public Property<Node> clipProperty() {
        return clipProperty;
    }

    private final Property<BlendMode> blendModeProperty = new SimpleObjectProperty<>();
    @Override
    public Property<BlendMode> blendModeProperty() {
        return blendModeProperty;
    }

    private final Property<Effect> effectProperty = new SimpleObjectProperty<>();
    @Override
    public Property<Effect> effectProperty() {
        return effectProperty;
    }

    private final Property<Double> layoutXProperty = new SimpleObjectProperty<>(0d);
    @Override
    public Property<Double> layoutXProperty() {
        return layoutXProperty;
    }

    private final Property<Double> layoutYProperty = new SimpleObjectProperty<>(0d);
    @Override
    public Property<Double> layoutYProperty() {
        return layoutYProperty;
    }

    private final ObservableList<Transform> transforms = FXCollections.observableArrayList();
    @Override
    public ObservableList<Transform> getTransforms() {
        return transforms;
    }

    private Translate layoutTransform;
    @Override
    public Collection<Transform> localToParentTransforms() {
        if (getLayoutX() == 0 && getLayoutY() == 0)
            return getTransforms();
        if (layoutTransform == null)
            layoutTransform = Translate.create();
        layoutTransform.setX(getLayoutX());
        layoutTransform.setY(getLayoutY());
        List<Transform> allTransforms = new ArrayList<>(transforms.size() + 1);
        allTransforms.add(layoutTransform);
        allTransforms.addAll(getTransforms());
        return allTransforms;
    }
}