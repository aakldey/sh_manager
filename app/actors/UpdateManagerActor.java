package actors;

import akka.actor.UntypedActor;
import controllers.Application;
import models.Device;
import models.Info;
import actors.UpdateManagerProtocol.*;
import actors.DeviceManagerProtocol.*;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class UpdateManagerActor extends UntypedActor {

    public List<Device> devices = new ArrayList<Device>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof UpdateDeviceMessage) {
            // Info
            devices.stream().forEach(device -> {
                Application.deviceManager.tell(new AskUpdateMessage(device), getSelf());
            });
        } else if (message instanceof SubscribeDeviceMessage) {
            Logger.info("subscribe new device for updates");
            Device device = ((SubscribeDeviceMessage) message).device;
            devices.add(device);
        } else if (message instanceof UnsubscribeDeviceMessage) {
            Device device = ((UnsubscribeDeviceMessage) message).device;
            devices.remove(device);
        }
    }
}
