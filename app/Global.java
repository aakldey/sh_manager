import com.avaje.ebean.Ebean;
import models.Switch;
import play.*;
import play.libs.Yaml;

import java.util.List;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if (Switch.find.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("initial-data.yml"));
        }
    }

    @Override
    public void onStop(Application app) {

    }
}
