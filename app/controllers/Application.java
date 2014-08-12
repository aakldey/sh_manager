package controllers;

import actors.DeviceManagerProtocol;
import actors.DeviceManagerProtocol.*;
import actors.TaskManagerProtocol;
import actors.TaskManagerProtocol.*;
import actors.UpdateManagerProtocol;
import actors.UpdateManagerProtocol.*;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static String FLASH_ERROR_KEY = "error";
    public static String FLASH_MESSAGE_KEY = "message";

    public static String DIGITAL_HIGH = "HIGH";
    public static String DIGITAL_LOW = "LOW";

    public static String API_PATH = "http://localhost:8080";

    public static ActorRef updateManager;
    public static ActorRef deviceManager;
    public static ActorRef taskManager;

    public static String DEVICE_TYPE_SWITCH = "switch";
    public static String DEVICE_TYPE_INFO = "info";
    public static String DEVICE_TYPE_SLIDER = "slider";

    public static DeviceGroup DEFAULT_DEVICE_GROUP = DeviceGroup.find.where().eq("name", DeviceGroup.DEFAULT_GROUP_NAME).findUnique();

    public static Result index() {
        return ok(index.render(DeviceGroup.find.all()));
    }

    public static Result tasks() {
        return ok(tasks.render());
    }

    public static Result config() {
        List<Device> list = new ArrayList();
        list.addAll(Switch.find.all());
        list.addAll(Info.find.all());
        list.addAll(Slider.find.all());
        return ok(config.render(list, DeviceGroup.find.all()));
    }

    public static Result updateSwitch(Long id) {
        try {
            DynamicForm form = Form.form().bindFromRequest();
            Switch sw = Switch.find.byId(id);
            if (sw != null) {
                String name = form.get("name");
                Long groupId = Long.parseLong(form.get("group"));
                int pinNumber = Integer.parseInt(form.get("pinNumber"));

                DeviceGroup group = DeviceGroup.find.byId(groupId);

                if (group == null) {
                    flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
                    return redirect(routes.Application.config());
                }
                updateManager.tell(new UnsubscribeDeviceMessage(sw), ActorRef.noSender());
                sw.name = name;
                sw.deviceGroup = group;
                sw.pinNumber = pinNumber;
                sw.save();
                updateManager.tell(new SubscribeDeviceMessage(sw), ActorRef.noSender()); // обновляем список в update manager
                taskManager.tell(new DeviceValueChangedMessage(sw), ActorRef.noSender());

                flash(Application.FLASH_MESSAGE_KEY, Messages.get("config.updated", sw.name));
                return redirect(routes.Application.config());
            } else {
                flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.nosuchdevice", id));
                return redirect(routes.Application.config());
            }
        } catch(Exception e) {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
            return redirect(routes.Application.config());
        }
    }

    public static Result updateInfo(Long id) {
        try {
            DynamicForm form = Form.form().bindFromRequest();
            Info info = Info.find.byId(id);
            if (info != null) {
                String name = form.get("name");
                String sign = form.get("sign");
                Long groupId = Long.parseLong(form.get("group"));
                int pinNumber = Integer.parseInt(form.get("pinNumber"));

                DeviceGroup group = DeviceGroup.find.byId(groupId);

                if (group == null) {
                    flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
                    return redirect(routes.Application.config());
                }

                updateManager.tell(new UnsubscribeDeviceMessage(info), ActorRef.noSender());
                info.name = name;
                info.deviceGroup = group;
                info.pinNumber = pinNumber;
                info.signature = sign;
                info.save();
                updateManager.tell(new SubscribeDeviceMessage(info), ActorRef.noSender());
                taskManager.tell(new DeviceValueChangedMessage(info), ActorRef.noSender());

                flash(Application.FLASH_MESSAGE_KEY, Messages.get("config.updated", info.name));
                return redirect(routes.Application.config());
            } else {
                flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.nosuchdevice", id));
                return redirect(routes.Application.config());
            }
        } catch (Exception e) {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
            return redirect(routes.Application.config());
        }
    }

    public static Result updateSlider(Long id) {
        try {
            DynamicForm form = Form.form().bindFromRequest();
            Slider slider = Slider.find.byId(id);
            if (slider != null) {
                String name = form.get("name");
                int rangeStart = Integer.parseInt(form.get("rangeStart"));
                int rangeEnd = Integer.parseInt(form.get("rangeEnd"));

                if (rangeEnd > 1023 || rangeStart < 0 || rangeEnd < rangeStart) {
                    flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.range"));
                    return redirect(routes.Application.config());
                }

                Long groupId = Long.parseLong(form.get("group"));
                int pinNumber = Integer.parseInt(form.get("pinNumber"));

                DeviceGroup group = DeviceGroup.find.byId(groupId);

                if (group == null) {
                    flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
                    return redirect(routes.Application.config());
                }

                updateManager.tell(new UnsubscribeDeviceMessage(slider), ActorRef.noSender());
                slider.name = name;
                slider.deviceGroup = group;
                slider.rangeStart = rangeStart;
                slider.rangeEnd = rangeEnd;
                slider.pinNumber = pinNumber;
                slider.save();
                updateManager.tell(new SubscribeDeviceMessage(slider), ActorRef.noSender());
                taskManager.tell(new DeviceValueChangedMessage(slider), ActorRef.noSender());

                flash(Application.FLASH_MESSAGE_KEY, Messages.get("config.updated", slider.name));
                return redirect(routes.Application.config());

            } else {
                flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.nosuchdevice", id));
                return redirect(routes.Application.config());
            }
        } catch(Exception e) {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
            return redirect(routes.Application.config());
        }
    }

    public static Result deleteSwitch(Long id) {
        Switch sw = Switch.find.byId(id);
        if (sw != null) {
            updateManager.tell(new UnsubscribeDeviceMessage(sw), ActorRef.noSender());
            sw.delete();
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.removed"));
            return redirect(routes.Application.config());
        } else {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.nosuchdevice", id));
            return redirect(routes.Application.config());
        }
    }

    public static Result deleteInfo(Long id) {
        Info info = Info.find.byId(id);
        if (info != null) {
            updateManager.tell(new UnsubscribeDeviceMessage(info), ActorRef.noSender());
            info.delete();
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.removed"));
            return redirect(routes.Application.config());
        } else {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.nosuchdevice", id));
            return redirect(routes.Application.config());
        }
    }

    public static Result deleteSlider(Long id) {
        Slider slider = Slider.find.byId(id);
        if (slider != null) {
            updateManager.tell(new UnsubscribeDeviceMessage(slider), ActorRef.noSender());
            slider.delete();
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.removed"));
            return redirect(routes.Application.config());
        } else {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error.nosuchdevice", id));
            return redirect(routes.Application.config());
        }
    }

    public static Result addDevice() {
        DynamicForm form = Form.form().bindFromRequest();
        String deviceType = form.get("type");

        if (deviceType.equals(DEVICE_TYPE_INFO) ||
                deviceType.equals(DEVICE_TYPE_SLIDER) ||
                deviceType.equals(DEVICE_TYPE_SWITCH)) {
            return ok(add.render(deviceType, DeviceGroup.find.all()));
        } else {
            flash(Application.FLASH_ERROR_KEY, Messages.get("config.error"));
            return redirect(routes.Application.config());
        }
    }

    public static Result addSwitch() {
        return TODO;
    }

    public static Result addInfo(){
        return TODO;
    }

    public static Result addSlider(){
        return TODO;
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
