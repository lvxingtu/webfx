package naga.providers.toolkit.swing.util;

import naga.toolkit.drawing.shapes.StrokeLineCap;
import naga.toolkit.drawing.shapes.StrokeLineJoin;

import java.awt.*;

/**
 * @author Bruno Salmon
 */
public class SwingStrokes {

    public static int toSwingStrokeLineCap(StrokeLineCap strokeLineCap) {
        if (strokeLineCap != null)
            switch (strokeLineCap) {
                case BUTT: return BasicStroke.CAP_BUTT;
                case ROUND: return BasicStroke.CAP_ROUND;
                case SQUARE: return BasicStroke.CAP_SQUARE;
            }
        return BasicStroke.CAP_SQUARE;
    }

    public static int toSwingStrokeLineJoin(StrokeLineJoin strokeLineJoin) {
        if (strokeLineJoin != null)
            switch (strokeLineJoin) {
                case MITER: return BasicStroke.JOIN_MITER;
                case BEVEL: return BasicStroke.JOIN_BEVEL;
                case ROUND: return BasicStroke.JOIN_ROUND;
            }
        return BasicStroke.JOIN_MITER;
    }

}