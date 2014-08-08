import actors.UpdateManagerActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.avaje.ebean.Ebean;
import models.Switch;
import play.*;
import play.libs.Akka;
import play.libs.Yaml;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Global extends GlobalSettings {

    public int UPDATE_INTERVAL = 500; // in ms

    @Override
    public void onStart(Application app) {
        if (Switch.find.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("initial-data.yml"));
        }

        ActorRef updateManager = Akka.system().actorOf(Props.create(UpdateManagerActor.class), "UpdateManager");
        Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.SECONDS),
                Duration.create(UPDATE_INTERVAL, TimeUnit.MILLISECONDS),
                updateManager,
                "update",
                Akka.system().dispatcher(),
                null
        );

    }

    @Override
    public void onStop(Application app) {

    }
}
