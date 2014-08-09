package models;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import play.Logger;
import play.libs.ws.*;
import play.db.ebean.Model;
import play.libs.F.*;

import javax.persistence.*;

@Entity
public class Info extends Model implements Device {

    @Id
    public Long id;

    public String name;

    public int pinNumber;

    public String signature;

    @ManyToOne
    public DeviceGroup deviceGroup;

    public int value;

    public Info(int pinNumber, String name) {
        this.pinNumber = pinNumber;
        this.name = name;
    }

    public static Finder<Long, Info> find = new Finder<Long, Info>(Long.class, Info.class);
}