package actors;

import akka.actor.UntypedActor;
import models.Info;

public class UpdateManagerActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg.equals("update")) {
            for(Info info : Info.find.all()) {
                info.updateValue();
            }
        }
    }
}
