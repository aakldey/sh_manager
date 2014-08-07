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
public class Info extends Model {

    @Id
    Long id;

    public String name;

    public int pinNumber;

    private int value;

    public Info(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
        this.value = getValue();
    }

    public int getValue() {
        try {
            Promise<WSResponse> result = WS.url(Application.API_PATH + "/analog/" + pinNumber).get();
            JsonNode json = result.get(10000).asJson();
            int value = json.get(Integer.toString(pinNumber)).asInt();
            this.value = value;
            Logger.info("INFO: getting analog pin " + pinNumber + " value");
            return value;
        } catch (Throwable e) {
            Logger.info("ERROR: error getting analog pin " + pinNumber + " value from Info " + name + ". \n" + e.getMessage());
            return -1;
        }
    }

    public static Finder<Long, Info> find = new Finder<Long, Info>(Long.class, Info.class);
}