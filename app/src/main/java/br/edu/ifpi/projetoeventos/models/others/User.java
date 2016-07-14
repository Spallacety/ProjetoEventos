package br.edu.ifpi.projetoeventos.models.others;

import br.edu.ifpi.projetoeventos.models.enums.EProfileType;

public class User {

    private String email;
    private String password;
    private String name;
    private EProfileType profileType;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String name, EProfileType profileType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileType = profileType;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public EProfileType getProfileType() {
        return this.profileType;
    }

}