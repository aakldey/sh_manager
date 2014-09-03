package models;

import actors.UpdateManagerProtocol;
import play.db.ebean.Model;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static controllers.Application.updateManager;

public class TaskActionDoLater extends TaskAction {

    public List<TaskAction> tasks;
    public final Long timeToWait;

    public TaskActionDoLater(Long time) {
        tasks = new ArrayList<TaskAction>();
        this.timeToWait = time;
    }

    @Override
    public void executeAction() {
        Akka.system().scheduler().scheduleOnce(Duration.create(timeToWait, TimeUnit.SECONDS),
                new Runnable() {
                    @Override
                    public void run() {
                        for(TaskAction task: tasks) {
                            task.executeAction();
                        }
                    }
                }, Akka.system().dispatcher());

    }



}
