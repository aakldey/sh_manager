package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DeviceGroup extends Model {

    public static String DEFAULT_GROUP_NAME = "Others";

    @Id
    public Long id;

    public String name;

    @OneToMany(mappedBy = "deviceGroup", cascade = CascadeType.ALL)
    public List<Device> devices = new ArrayList<Device>();

    public DeviceGroup(String name) {
        this.name = name;
        this.save();
    }

    public int getDeviceCount() {
        return devices.size();
    }

    public static Finder<Long, DeviceGroup> find = new Finder<Long, DeviceGroup>(Long.class, DeviceGroup.class);
}
