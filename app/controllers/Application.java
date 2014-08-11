package controllers;

import actors.DeviceManagerProtocol;
import actors.DeviceManagerProtocol.*;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
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

    public static ActorRef updateManager;
    public static ActorRef deviceManager;
    public static ActorRef taskManager;

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

    public static Result getInfo(Long id) {
        Info info = Info.find.byId(id);
        if (info != null) {
            return ok(Json.toJson(info));
        } else {
            return badRequest();
        }
        //return TODO;
    }

    public static Result switchValue(Long id) {
        Switch sw = Switch.find.byId(id);
        if (sw != null) {
            sw.value = !sw.value;
            deviceManager.tell(new ChangeDeviceValue(sw), ActorRef.noSender());
            return ok();
        } else {
            return badRequest();
        }
    }

    public static Result getSlider(Long id) {
        Slider slider = Slider.find.byId(id);
        if (slider != null) {
            return ok(Json.toJson(slider));
        } else {
            return badRequest();
        }
    }

    public static Result getSwitch(Long id) {
        Switch sw = Switch.find.byId(id);
        if (sw != null) {
            return ok(Json.toJson(sw));
        } else {
            return badRequest();
        }
    }

    public static Result setSliderValue(Long id, int value) {
        Slider slider = Slider.find.byId(id);
        if (slider != null) {
            if (value >= 0 && value < 1024) {
                slider.value = value;
                Logger.info(value + "");
                deviceManager.tell(new ChangeDeviceValue(slider), ActorRef.noSender());
                return ok();
            } else {
                return badRequest();
            }
        } else {
            return badRequest();
        }
    }

}
