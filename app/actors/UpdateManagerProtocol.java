package actors;

import models.Device;

import java.util.List;

public abstract class UpdateManagerProtocol {
    public static class UpdateDeviceMessage {

        public UpdateDeviceMessage() {
        }
    }

    public static class SubscribeDeviceMessage {

        public final Device device;

        public SubscribeDeviceMessage(Device device) {
            this.device = device;
        }
    }

    public static class UnsubscribeDeviceMessage {
        public final Device device;

        public UnsubscribeDeviceMessage(Device device) {
            this.device = device;
        }
    }
}
