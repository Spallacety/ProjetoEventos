package br.edu.ifpi.projetoeventos.exceptions;

public class ActivityIsNotFromThisEventException extends Exception {

    public ActivityIsNotFromThisEventException(){
        super("Selected activity is not from event of inscription");
    }

}
