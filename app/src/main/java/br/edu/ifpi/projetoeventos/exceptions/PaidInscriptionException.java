package br.edu.ifpi.projetoeventos.exceptions;

public class PaidInscriptionException extends Exception {

    public PaidInscriptionException(){
        super("Inscription already paid");
    }

}
