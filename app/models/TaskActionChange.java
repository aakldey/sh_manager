package models;

import play.Logger;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



public class TaskActionChange extends TaskAction {

    @Override
    public void executeAction() {
        Logger.info("evaluting action");
    }


}
