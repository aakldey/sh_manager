package actors;

import models.*;

public abstract class TaskManagerProtocol {
    public static class DeviceValueChangedMessage {

        public final Device device;

        public DeviceValueChangedMessage(Device device) {
            this.device = device;
        }
    }

    public static class SubscribeTaskMessage {
        public final Task task;

        public SubscribeTaskMessage(Task task) {
            this.task = task;
        }
    }

    public static class UnsubscribeTaskMessage {
        public final Task task;

        public UnsubscribeTaskMessage(Task task) {
            this.task = task;
        }
    }

}
