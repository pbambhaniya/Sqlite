package com.multipz.sqllitedemo;

/**
 * Created by Admin on 24-03-2018.
 */

public class Model {
//    public Model(String event_name, String event_time) {
//        this.event_name = event_name;
//        this.event_time = event_time;
//    }

    String event_name;
    int id;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String event_time;
}
