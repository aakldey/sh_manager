package actors;

import models.Device;

public abstract class DeviceManagerProtocol {

    public static class AskUpdateMessage {

        public final Device device;

        public AskUpdateMessage(Device device) {
            this.device = device;
        }

    }
}
