package br.edu.ifpi.projetoeventos.models.event;

import java.math.BigDecimal;

import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.event.Event;

public class Activity {

	protected BigDecimal value;
	protected String name;
	protected Event event;
	private ActivityType activityType;
	
	public Activity(String name, String value, ActivityType activityType){
		this.name = name;
		this.value = new BigDecimal(value);
		this.activityType = activityType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public ActivityType getActivityType(){
		return this.activityType;
	}
}
