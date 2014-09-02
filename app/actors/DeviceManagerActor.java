package actors;

import akka.actor.UntypedActor;
import controllers.Application;
import models.*;
import actors.DeviceManagerProtocol.*;
import actors.TaskManagerProtocol.*;

import play.Logger;
import play.libs.F.*;
import models.Device.*;
import play.libs.ws.*;
import scala.App;

import java.util.ArrayList;
import java.util.List;

public class DeviceManagerActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof AskUpdateMessage) {
            Device device = ((AskUpdateMessage) message).device;
            if (device.deviceType == DeviceType.SWITCH ) {

                Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + device.pinNumber).get();
                result.onRedeem(response -> {
                    int newValue = response.asJson().get(Integer.toString(device.pinNumber)).asText().equals(Application.DIGITAL_HIGH)?1:0;
                    if (device.value != newValue) {
                        device.value = newValue;
                        device.save();
                        Application.taskManager.tell(new DeviceValueChangedMessage(device), getSelf());
                    }
                });
            } else if (device.deviceType == DeviceType.INFO) {
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/" + device.pinNumber).get();
                result.onRedeem(response -> {
                    int newValue = response.asJson().get(Integer.toString(device.pinNumber)).asInt();
                    if (device.value != newValue) {
                        device.value = newValue;
                        device.save();
                        Application.taskManager.tell(new DeviceValueChangedMessage(device), getSelf());
                    }

                });
            } else if (device.deviceType == DeviceType.SLIDER) {
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + device.pinNumber).get();
                result.onRedeem(response -> {
                    Logger.info(response.getBody().toString());
                });
            }
        } else if (message instanceof ChangeDeviceValue) {
            Device device = ((ChangeDeviceValue) message).device;

            if (device.deviceType == DeviceType.SWITCH) {
                String value = device.value > 0?Application.DIGITAL_HIGH:Application.DIGITAL_LOW;
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + device.pinNumber + "/" + value).get();
                Application.taskManager.tell(new DeviceValueChangedMessage(device), getSelf());
            } else if (device.deviceType == DeviceType.SLIDER) {
                Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/"+ device.pinNumber + "/" + device.value).get();
                Application.taskManager.tell(new DeviceValueChangedMessage(device), getSelf());
            }

        } else
            unhandled(message);
    }

}
