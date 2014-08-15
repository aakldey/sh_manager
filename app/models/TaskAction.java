package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TaskAction extends Model{

    @Id
    public Long id;

    @ManyToOne
    public Task task;

    public void executeAction() {

    }

    public static Model.Finder<Long, TaskAction> find = new Model.Finder<Long, TaskAction>(Long.class, TaskAction.class);

}
