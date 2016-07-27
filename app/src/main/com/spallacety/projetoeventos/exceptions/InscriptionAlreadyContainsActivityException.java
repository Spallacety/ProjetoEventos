package br.edu.ifpi.projetoeventos.exceptions;

public class InscriptionAlreadyContainsActivityException extends Exception {

    public InscriptionAlreadyContainsActivityException(){
        super("This inscription already contains this activity");
    }
}
