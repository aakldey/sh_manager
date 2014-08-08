package models;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import play.Logger;
import play.libs.ws.*;
import play.db.ebean.Model;
import play.libs.F.*;

import javax.persistence.*;

@Entity
public class Info extends Model {

    @Id
    public Long id;

    public String name;

    public int pinNumber;

    public String signature;

    @ManyToOne
    public DeviceGroup deviceGroup;

    private int value;

    public Info(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
        updateValue();
    }

    public void updateValue() {
        try {
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/" + pinNumber).get();
            JsonNode json = result.get(10000).asJson();
            int value = json.get(Integer.toString(pinNumber)).asInt();
            this.value = value;
            this.save();
            Logger.info("getting analog pin " + pinNumber + " value");
        } catch (Throwable e) {
            Logger.error("error getting analog pin " + pinNumber + " value from Info " + name + ". \n" + e.getMessage());
        }
    }

    public int getValue() {
        return value;
    }

    public static Finder<Long, Info> find = new Finder<Long, Info>(Long.class, Info.class);
}