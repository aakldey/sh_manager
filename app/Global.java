import actors.DeviceManagerActor;
import actors.TaskManagerActor;
import actors.TaskManagerProtocol;
import actors.TaskManagerProtocol.*;
import actors.UpdateManagerActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.avaje.ebean.Ebean;
import models.*;
import play.*;
import play.libs.Akka;
import play.libs.Yaml;
import scala.concurrent.duration.Duration;
import static controllers.Application.*;
import actors.UpdateManagerProtocol.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Global extends GlobalSettings {

    public int UPDATE_INTERVAL = 500; // in ms

    @Override
    public void onStart(Application app) {
        if (Switch.find.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("initial-data.yml"));
        }

        updateManager = Akka.system().actorOf(Props.create(UpdateManagerActor.class), "UpdateManager");
        deviceManager = Akka.system().actorOf(Props.create(DeviceManagerActor.class), "DeviceManager");
        taskManager = Akka.system().actorOf(Props.create(TaskManagerActor.class), "TaskManager");

        List<Device> devices = new ArrayList<Device>();

        Switch.find.all().stream().forEach(sw -> {
            devices.add(sw);
        });

        Info.find.all().stream().forEach(info -> {
            devices.add(info);
        });


        Slider.find.all().stream().forEach(slider -> {
            devices.add(slider);
        });

        devices.stream().forEach(device -> {
            updateManager.tell(new SubscribeDeviceMessage(device), ActorRef.noSender());
        });

        Task task = new Task("task1");

        TaskCondition cond = new TaskCondition(Info.find.where().eq("name", "Temperature").findUnique(),70, TaskCondition.ConditionType.GREATER);
        task.conditions.add(cond);
        TaskActionChange action = new TaskActionChange();
        task.actions.add(action);

        taskManager.tell(new SubscribeTaskMessage(task), ActorRef.noSender());

        Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.SECONDS),
                Duration.create(UPDATE_INTERVAL, TimeUnit.MILLISECONDS),
                updateManager,
                new UpdateDeviceMessage(),
                Akka.system().dispatcher(),
                null
        );

    }

    @Override
    public void onStop(Application app) {

    }
}
