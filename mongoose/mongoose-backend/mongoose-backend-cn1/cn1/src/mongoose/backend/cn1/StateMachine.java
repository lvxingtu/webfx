/**
 * Your application code goes here<br>
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */


package mongoose.backend.cn1;

import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import generated.StateMachineBase;
import mongoose.activities.MongooseBackendApplication;
import naga.commons.bus.websocket.WebSocketBusOptions;
import naga.core.spi.platform.Platform;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    @Override
    protected void initVars(Resources res) {
    }

    @Override
    protected void beforeMain(Form f) {
    }

    @Override
    protected void postMain(Form f) {
        MongooseBackendApplication.main(null);
    }

}
