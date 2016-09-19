package br.edu.ifpi.projetoeventos.models.event;

import br.edu.ifpi.projetoeventos.models.enums.ActivityType;

public class Factory {

    public static Activity makeActivity(){
        return new Activity();
    }

    public static Activity makeActivityWithID(String id){
        return new Activity(id);
    }

    public static Activity makeLecture(){
        Activity a = makeActivity();
        a.setActivityType(ActivityType.LECTURE);
        return a;
    }

    public static Activity makePanelDiscussion(){
        Activity a = makeActivity();
        a.setActivityType(ActivityType.PANEL_DISCUSSION);
        return a;
    }

    public static Activity makeMinicourse(){
        Activity a = makeActivity();
        a.setActivityType(ActivityType.MINICOURSE);
        return a;
    }

    public static Event makeEvent(){
        return new Event();
    }

    public static Event makeEventWithID(String id){
        return new Event(id);
    }

    public static Location makeLocation(){
        return new Location();
    }

    public static Location makeLocationWithID(String id){
        return new Location(id);
    }

    public static User makeUser(){
        return new User();
    }

    public static User makeUserWithID(String id){
        return new User(id);
    }

}