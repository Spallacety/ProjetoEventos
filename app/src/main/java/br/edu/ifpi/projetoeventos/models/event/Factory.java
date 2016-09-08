package br.edu.ifpi.projetoeventos.models.event;

public class Factory {

    public static Activity makeActivity(){
        return new Activity();
    }

    public static Event makeEvent(){
        return new Event();
    }

    public static Location makeLocation(){
        return new Location();
    }
}