package br.edu.ifpi.projetoeventos.models.enums;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.R;

/**
 * Created by Lucas on 31/08/2016.
 */
public enum LocationType {

    NONE(R.string.none),
    BUILDING(R.string.building),
    FLOOR(R.string.floor),
    ROOM(R.string.room),
    AUDITORIUM(R.string.auditorium),
    LABORATORY(R.string.laboratory);

    int stringId;

    LocationType(int stringId){
        this.stringId = stringId;
    }

    public String getTranslatedName(Context context) {
        return context.getString(stringId);
    }

    public static List<String> getPossibleRelatedLocations(Context context, LocationType locationType){
        if(locationType == null || locationType == NONE) {return getAllTranslatedNames(context);
        } else {
            List<String> translatedNames = getAllTranslatedNames(context);
            int index = translatedNames.indexOf(locationType.getTranslatedName(context));
            List<String> result = new ArrayList<>();
            result.add(NONE.getTranslatedName(context));
            result.addAll((index > 2) ? new ArrayList<String>() : translatedNames.subList(index+1, translatedNames.size()));
            return result;
        }
    }

    public static List<String> getAllTranslatedNames(Context context){
        List<String> spinnerArray = new ArrayList<>();
        for (LocationType t : LocationType.values()){
            spinnerArray.add(t.getTranslatedName(context));
        }
        return spinnerArray;
    }

    public static LocationType fromTranslatedName(Context context, String translatedName){
        for (LocationType t : values()) {
            String translated = t.getTranslatedName(context);
            if(translated.toLowerCase().equals(translatedName.toLowerCase())) return t;
        }
        throw new IllegalArgumentException("Enum name doesn't exists");
    }

    public static LocationType fromName(String name){
        for (LocationType t : values()) {
            if(t.name().toLowerCase().equals(name.toLowerCase())) return t;
        }
        throw new IllegalArgumentException("Enum name doesn't exists");
    }

}
