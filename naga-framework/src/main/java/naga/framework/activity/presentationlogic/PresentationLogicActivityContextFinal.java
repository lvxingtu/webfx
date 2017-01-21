package naga.framework.activity.presentationlogic;

import naga.platform.activity.ActivityContext;

/**
 * @author Bruno Salmon
 */
public class PresentationLogicActivityContextFinal<PM>
        extends PresentationLogicActivityContextBase<PresentationLogicActivityContextFinal<PM>, PM> {

    public PresentationLogicActivityContextFinal(ActivityContext parentContext) {
        super(parentContext, PresentationLogicActivityContextFinal::new);
    }
}