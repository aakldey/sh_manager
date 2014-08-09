package actors;

import akka.actor.UntypedActor;
import controllers.Application;
import models.*;
import actors.DeviceManagerProtocol.*;
import actors.TaskManagerProtocol.*;

import play.Logger;
import play.libs.F.*;
import play.libs.ws.*;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DeviceValueChangedMessage) {
            Logger.info("device value changed");
        } else
            unhandled(message);
    }
}
