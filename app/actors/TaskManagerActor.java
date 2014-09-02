package actors;

import akka.actor.UntypedActor;
import models.*;
import actors.TaskManagerProtocol.*;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActor extends UntypedActor {

    public List<Task> tasks = new ArrayList<>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DeviceValueChangedMessage) {
        /*    tasks.stream().filter(task -> {
                if (task.conditions.stream().filter(condition -> {
                    Device device = (Device)message;
                    if (device.getClass().isInstance(device)) {
                        if (device.deviceType == Device.DeviceType.SWITCH) {
                            if (((Switch)device).id == ((Switch)condition.device).id)
                                return true;
                            else
                                return false;
                        } else if (device instanceof Info) {
                            if (((Info)device).id == ((Info)condition.device).id)
                                return true;
                            else
                                return false;
                        } else if (device instanceof Slider) {
                            if (((Slider)device).id == ((Slider)condition.device).id)
                                return true;
                            else
                                return false;
                        } else
                            return false;
                    } else
                        return false;

                }).count() > 0)
                    return true;
                else
                    return false;
            }).forEach(task -> {
                if (task.checkConditions())
                    task.executeTask();
            });*/
        } else if (message instanceof SubscribeTaskMessage) {
            tasks.add(((SubscribeTaskMessage) message).task);
        } else if (message instanceof UnsubscribeTaskMessage) {
            tasks.remove(((UnsubscribeTaskMessage) message).task);
        } else
            unhandled(message);
    }
}
