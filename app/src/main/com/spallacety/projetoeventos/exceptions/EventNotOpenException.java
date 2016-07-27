package br.edu.ifpi.projetoeventos.exceptions;

public class EventNotOpenException extends Exception {

    public EventNotOpenException(){
        super("This event is not open");
    }

}
