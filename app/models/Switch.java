package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import play.Logger;
import play.libs.ws.*;
import play.db.ebean.Model;
import play.libs.F.*;
import play.mvc.Http;

import javax.persistence.*;

@Entity
public class Switch extends Model implements Device {

    @Id
    public Long id;

    public String name;

    public int pinNumber;

    @JsonIgnore
    @ManyToOne
    public DeviceGroup deviceGroup;

    public boolean value;

    public Switch(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
    }

    public static Finder<Long, Switch> find = new Finder<Long, Switch>(Long.class, Switch.class);
}
