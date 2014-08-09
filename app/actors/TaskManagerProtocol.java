package actors;

import models.*;

public abstract class TaskManagerProtocol {
    public static class DeviceValueChangedMessage {

        public final Device device;

        public DeviceValueChangedMessage(Device device) {
            this.device = device;
        }
    }
}
