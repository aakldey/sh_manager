package models;

import actors.DeviceManagerProtocol;
import actors.TaskManagerProtocol;
import akka.actor.ActorRef;
import controllers.Application;
import play.Logger;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



public class TaskActionChange extends TaskAction {

    public Device deviceToChange;
    public int newValue;

    public TaskActionChange(Device deviceToChange, int newValue) {
        this.deviceToChange = deviceToChange;
        this.newValue = newValue;
        deviceToChange.value = newValue;
    }

    @Override
    public void executeAction() {
        Logger.info("changing " + deviceToChange.name + "value to " + newValue);
        Application.deviceManager.tell(new DeviceManagerProtocol.ChangeDeviceValue(deviceToChange), ActorRef.noSender());
    }


}
