package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import models.*;

public class Application extends Controller {

    public static String FLASH_ERROR_KEY = "error";
    public static String FLASH_MESSAGE_KEY = "message";

    public static String DIGITAL_HIGH = "HIGH";
    public static String DIGITAL_LOW = "LOW";

    public static String API_PATH = "http://localhost:8080";

    public static DeviceGroup DEFAULT_DEVICE_GROUP = DeviceGroup.find.where().eq("name", DeviceGroup.DEFAULT_GROUP_NAME).findUnique();

    public static Result index() {
        return ok(index.render(DeviceGroup.find.all()));
    }

    public static Result tasks() {
        return ok(tasks.render());
    }

    public static Result config() {
        return ok(config.render());
    }

    public static Result getInfoValue(Long id) {
        ObjectNode result = Json.newObject();
        Info info = Info.find.byId(id);
        if (info != null) {
            result.put("value", info.getValue());
            return ok(result);
        } else {
            return badRequest();
        }
    }

}
