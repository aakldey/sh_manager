package models;

import controllers.Application;
import play.api.libs.ws.WSRequest;
import play.db.ebean.Model;
import play.libs.F;
import play.libs.ws.WS;
import models.Device.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TaskCondition extends Model {

    public enum ConditionType {
        EQ, LESS_EQ, LESS, GREATER, GREATER_EQ, NOT_EQ
    }


    public Long id;

    public final Device device;
    public final int value;
    public final ConditionType conditionType;

    @ManyToOne
    public Task task;

    public TaskCondition(Device device, int value, ConditionType conditionType) {
        this.device = device;
        this.value = value;
        this.conditionType = conditionType;
    }

    public boolean checkCondition() {

        Device updatedDevice= Device.find.byId(device.id);

            switch (conditionType) {
                case EQ:
                    if (updatedDevice.value == value)
                        return true;
                    else
                        return false;
                case NOT_EQ:
                    if (updatedDevice.value != value)
                        return true;
                    else
                        return false;
                case LESS:
                    if (updatedDevice.value < value)
                        return true;
                    else
                        return false;
                case LESS_EQ:
                    if (updatedDevice.value <= value)
                        return true;
                    else
                        return false;
                case GREATER:
                    if (updatedDevice.value > value)
                        return true;
                    else
                        return false;
                case GREATER_EQ:
                    if (updatedDevice.value >= value)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }
    }

    public static Finder<Long, TaskCondition> find = new Finder<Long, TaskCondition>(Long.class, TaskCondition.class);
}
