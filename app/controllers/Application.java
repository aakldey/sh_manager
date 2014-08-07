package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.*;

public class Application extends Controller {

    public static String FLASH_ERROR_KEY = "error";
    public static String FLASH_MESSAGE_KEY = "message";

    public static String DIGITAL_HIGH = "HIGH";
    public static String DIGITAL_LOW = "LOW";

    public static String API_PATH = "http://localhost:8080";

    public static Result index() {
        Switch sw1 = new Switch(3, "Lamp 1");
        sw1.setSwitched(true);

        return ok(index.render());
    }

}
