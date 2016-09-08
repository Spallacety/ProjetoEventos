package br.edu.ifpi.projetoeventos.models.others;

import java.util.Map;

public abstract class Mappable {

    private String ID;

    public abstract void fromMap(Map<String, Object> map);

    public abstract Map<String, Object> toMap();

    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

}