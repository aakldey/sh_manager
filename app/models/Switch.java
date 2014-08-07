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

    private boolean value;

    public Switch(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
    }

    public boolean getValue() {
        try {
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + pinNumber).get();
            JsonNode json = result.get(10000).asJson();
            if (json.get(Integer.toString(pinNumber)).asText().equals(Application.DIGITAL_LOW)) {
                value = false;
            } else {
                value = true;
            }
            Logger.info("INFO: getting digital pin " + pinNumber + " value");
        } catch (Throwable e) {
            Logger.info("ERROR: error getting digital pin " + pinNumber + " value from Switch " + name + ". \n" + e.getMessage());
        }
        return value;
    }

    public void setValue(boolean value) {
        try {
            String val = value?Application.DIGITAL_HIGH:Application.DIGITAL_LOW;
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/digital/" + pinNumber + "/" + val).get();
            if (result.get(10000).getStatus() == Http.Status.OK) {
                Logger.info("INFO: setting digital pin " + pinNumber + " value to: " + val);
                this.value = value;
            }
            Logger.info(result.get(1000).getStatusText());
        } catch (Throwable e) {
            Logger.info("ERROR: error setting digital pin " + pinNumber + " value from Switch " + name + ". \n" + e.getMessage());
        }
    }

    public static Finder<Long, Switch> find = new Finder<Long, Switch>(Long.class, Switch.class);
}
