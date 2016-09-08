package br.edu.ifpi.projetoeventos.models.enums;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.R;

public enum EventStatus{

	IN_PROGRESS(R.string.event_status_in_progress),
	OPEN(R.string.event_status_open),
	FINISHED(R.string.event_status_finished),
	CANCELED(R.string.event_status_canceled);

	int stringId;

	EventStatus(int stringId){
		this.stringId = stringId;
	}

	public String getTranslatedName(Context context) {
		return context.getString(stringId);
	}

	public static List<String> getAllTranslatedNames(Context context){
		List<String> spinnerArray = new ArrayList<>();
		for (EventStatus t : EventStatus.values()){
			spinnerArray.add(t.getTranslatedName(context));
		}
		return spinnerArray;
	}

	public static EventStatus fromTranslatedName(Context context, String translatedName){
		for (EventStatus t : values()) {
			String translated = t.getTranslatedName(context);
			if(translated.toLowerCase().equals(translatedName.toLowerCase())) return t;
		}
		throw new IllegalArgumentException("Enum name doesn't exists");
	}

	public static EventStatus fromName(String name){
		for (EventStatus t : values()) {
			if(t.name().toLowerCase().equals(name.toLowerCase())) return t;
		}
		throw new IllegalArgumentException("Enum name doesn't exists");
	}
}
