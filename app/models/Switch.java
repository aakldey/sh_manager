package models;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import play.Logger;
import play.libs.ws.*;
import play.db.ebean.Model;
import play.libs.F.*;
import play.mvc.Http;

import javax.persistence.*;

@Entity
public class Switch extends Model {

    @Id
    public Long id;

    public String name;

    public int pinNumber;

    @ManyToOne
    public DeviceGroup deviceGroup;

    private boolean value;

    public Switch(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
    }

    public boolean updateValue() {
        try {
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + pinNumber).get();
            JsonNode json = result.get(10000).asJson();
            if (json.get(Integer.toString(pinNumber)).asText().equals(Application.DIGITAL_LOW)) {
                value = false;
            } else {
                value = true;
            }
            this.save();
           // Logger.info("getting digital pin " + pinNumber + " value");
        } catch (Throwable e) {
            Logger.error("error getting digital pin " + pinNumber + " value from Switch " + name + ". \n" + e.getMessage());
        }
        return value;
    }

    public boolean getValue() {
        this.update();
        return value;
    }

    public void setValue(boolean value) {
        try {
            String val = value?Application.DIGITAL_HIGH:Application.DIGITAL_LOW;
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + pinNumber + "/" + val).get();
            if (result.get(10000).getStatus() == Http.Status.OK) {
                Logger.info("setting digital pin " + pinNumber + " value to: " + val);
                this.value = value;
            } else {
                Logger.info(result.get(10000).getStatusText());
            }
        } catch (Throwable e) {
            Logger.error("error setting digital pin " + pinNumber + " value from Switch " + name + ". \n" + e.getMessage());
        }
    }

    public static Finder<Long, Switch> find = new Finder<Long, Switch>(Long.class, Switch.class);
}
