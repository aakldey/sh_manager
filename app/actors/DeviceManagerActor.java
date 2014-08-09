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

public class DeviceManagerActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof AskUpdateMessage) {
            Device device = ((AskUpdateMessage) message).device;
            if (device instanceof Switch) {
                Switch sw = (Switch)device;
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + sw.pinNumber).get();
                result.onRedeem(response -> {
                    boolean newValue = response.asJson().get(Integer.toString(sw.pinNumber)).asText().equals(Application.DIGITAL_HIGH)?true:false;
                    if (sw.value != newValue) {
                        sw.value = newValue;
                        sw.save();
                        Application.taskManager.tell(new DeviceValueChangedMessage(sw), getSelf());
                    }
                });
            } else if (device instanceof Info) {
                Info info = (Info)device;
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/" + info.pinNumber).get();
                result.onRedeem(response -> {
                    int newValue = response.asJson().get(Integer.toString(info.pinNumber)).asInt();
                    if (info.value != newValue) {
                        info.value = newValue;
                        info.save();
                        Application.taskManager.tell(new DeviceValueChangedMessage(info), getSelf());
                    }

                });
            } else if (device instanceof Slider) {
                Slider sw = (Slider)device;
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + sw.pinNumber).get();
                result.onRedeem(response -> {
                    Logger.info(response.getBody().toString());
                });
            } else
                unhandled(message);
        }
    }

}
