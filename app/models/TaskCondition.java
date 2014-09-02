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
       /* if (device instanceof Switch) {
            Switch sw = (Switch)device;
            Switch currentSw = Switch.find.byId(sw.id);

            switch (conditionType) {
                case EQ:
                    if (currentSw.value == value)
                        return true;
                    else
                        return false;
                case NOT_EQ:
                    if (currentSw.value != value)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }
        } else if (device instanceof Info) {
            Info info = (Info)device;
            Info currentInfo = Info.find.byId(info.id);

            switch (conditionType) {
                case EQ:
                    if (currentInfo.value == value)
                        return true;
                    else
                        return false;
                case NOT_EQ:
                    if (currentInfo.value != value)
                        return true;
                    else
                        return false;
                case LESS:
                    if (currentInfo.value < value)
                        return true;
                    else
                        return false;
                case LESS_EQ:
                    if (currentInfo.value <= value)
                        return true;
                    else
                        return false;
                case GREATER:
                    if (currentInfo.value > value)
                        return true;
                    else
                        return false;
                case GREATER_EQ:
                    if (currentInfo.value >= value)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }
        } else if (device instanceof Slider) {
            Slider slider = (Slider)device;
            Slider currentSlider = Slider.find.byId(slider.id);

            switch (conditionType) {
                case EQ:
                    if (currentSlider.value == value)
                        return true;
                    else
                        return false;
                case NOT_EQ:
                    if (currentSlider.value != value)
                        return true;
                    else
                        return false;
                case LESS:
                    if (currentSlider.value < value)
                        return true;
                    else
                        return false;
                case LESS_EQ:
                    if (currentSlider.value <= value)
                        return true;
                    else
                        return false;
                case GREATER:
                    if (currentSlider.value > value)
                        return true;
                    else
                        return false;
                case GREATER_EQ:
                    if (currentSlider.value >= value)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }
        } else
            return false;
            */
        return false;
    }

    public static Finder<Long, TaskCondition> find = new Finder<Long, TaskCondition>(Long.class, TaskCondition.class);
}
