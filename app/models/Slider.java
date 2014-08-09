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
public class Slider extends Model implements Device {

    @Id
    public Long id;

    public String name;

    public int pinNumber;

    public int rangeStart;

    public int rangeEnd;

    @ManyToOne
    public DeviceGroup deviceGroup;

    public int value;

    public Slider(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
    }

    public static Finder<Long, Slider> find = new Finder<>(Long.class, Slider.class);

}