package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class Device extends Model {

    public enum DeviceType {
        SWITCH, INFO, SLIDER
    }

    @Id
    public Long id;

    public String name;

    public int value;

    public int pinNumber;

    //slider properties
    public int rangeStart = 0;

    public int rangeEnd = 0;

    //info property
    public String signature = "";

    public DeviceType deviceType;

    @JsonIgnore
    @ManyToOne
    public DeviceGroup deviceGroup;

    public Device() {

    }

    public Device(String name, int pinNumber, int value, DeviceType type) {
        this.name = name;
        this.pinNumber = pinNumber;
        this.value = value;
        this.deviceType = type;
    }

    public static Model.Finder<Long, Device> find = new Model.Finder<Long, Device>(Long.class, Device.class);


}
