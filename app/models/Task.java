package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task extends Model{

    @Id
    public Long id;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<TaskCondition> conditions;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<TaskAction> actions;

    public String name;

    public Task(String name) {
        this.name = name;
        conditions = new ArrayList<>();
        actions = new ArrayList<>();
        this.save();

    }

    public void executeTask() {
       // actionsChange.forEach(action -> action.executeAction());
        for(TaskAction change : actions)
            change.executeAction();
        //actionsDoLater.forEach(action -> action.executeAction());
    }

    public boolean checkConditions() {

        for (TaskCondition condition : conditions) {
            if (!condition.checkCondition()) {
                return false;
            }

        }
        return true;
    }

    public static Finder<Long, Task> find = new Finder<Long, Task>(Long.class, Task.class);

}
