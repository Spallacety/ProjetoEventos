package br.edu.ifpi.projetoeventos.models.enums;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.R;

public enum ActivityType {

    LECTURE(R.string.lecture),
    MINICOURSE(R.string.minicourse),
    PANEL_DISCUSSION(R.string.panel_discussion),
    REGISTRATION(R.string.registration),
    INTERVAL(R.string.interval),
    COFFEEBREAK(R.string.coffeebreak);

    int stringId;

    ActivityType(int stringId){
        this.stringId = stringId;
    }

    public String getTranslatedName(Context context) {
        return context.getString(stringId);
    }

    public static List<String> getAllTranslatedNames(Context context){
        List<String> spinnerArray = new ArrayList<>();
        for (ActivityType t : ActivityType.values()){
            spinnerArray.add(t.getTranslatedName(context));
        }
        return spinnerArray;
    }

    public static ActivityType fromTranslatedName(Context context, String translatedName){
        for (ActivityType t : values()) {
            String translated = t.getTranslatedName(context);
            if(translated.toLowerCase().equals(translatedName.toLowerCase())) return t;
        }
        throw new IllegalArgumentException("Enum name doesn't exists");
    }

    public static ActivityType fromName(String name){
        for (ActivityType t : values()) {
            if(t.name().toLowerCase().equals(name.toLowerCase())) return t;
        }
        throw new IllegalArgumentException("Enum name doesn't exists");
    }

}
