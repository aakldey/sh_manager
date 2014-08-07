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
public class Slider extends Model {

    @Id
    public Long id;

    public String name;

    public int pinNumber;

    private int value;

    public Slider(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
        this.value = getValue();
    }

    public int getValue() {
        try {
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/" + pinNumber).get();
            JsonNode json = result.get(10000).asJson();
            value = json.get(Integer.toString(pinNumber)).asInt();
            Logger.info("INFO: getting analog pin " + pinNumber + " value");
            return value;
        } catch (Throwable e) {
            Logger.info("ERROR: error getting analog pin " + pinNumber + " value from Slider " + name + ". \n" + e.getMessage());
        }
        return value;
    }

    public void setValue(int val) {
        try {
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/" + pinNumber + "/" + val).get();
            if (result.get(10000).getStatus() == Http.Status.OK) {
                Logger.info("INFO: setting pin " + pinNumber + " value to: " + val);
                this.value = value;
            }
            Logger.info(result.get(1000).getStatusText());
        } catch (Throwable e) {
            Logger.info("ERROR: error setting analog pin " + pinNumber + " value from Slider " + name + ". \n" + e.getMessage());
        }
    }

}