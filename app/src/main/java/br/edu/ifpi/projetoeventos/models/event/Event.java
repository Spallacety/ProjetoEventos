package br.edu.ifpi.projetoeventos.models.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.edu.ifpi.projetoeventos.models.enums.EventStatus;
import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.location.Location;

public class Event {

	protected String name;
	protected Location location;
	private Event relatedEvent;
	protected List<Activity> activityList = new ArrayList<>();
	protected EventStatus status;
	private EventType eventType;
	protected Calendar initialDate;

	public Event(EventType eventType){
		this.status = EventStatus.OPEN;
		this.initialDate = Calendar.getInstance();
		this.initialDate.set(Calendar.YEAR, 1970);
		this.initialDate.set(Calendar.MONTH, 0);
		this.initialDate.set(Calendar.DAY_OF_MONTH, 1);
		this.eventType = eventType;
	}

	public boolean addActivity(Activity activity){
		if(containsActivity(this.activityList, activity)){
			return false;
		}
		activity.setEvent(this);
		this.activityList.add(activity);
		return true;
	}

	private boolean containsActivity(List<Activity> activityList, Activity activity) {
		for (Activity a : activityList) {
			if(a.hashCode() == activity.hashCode()) return true;
		}
		return false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Activity> getActivityList() {
		return this.activityList;
	}

	public EventStatus getStatus() {
		return this.status;
	}

	public Calendar getInitialDate() {
		return this.initialDate;
	}

	public void setInitialDate(int day, int month, int year) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, (month-1));
		date.set(Calendar.DAY_OF_MONTH, day);
		if(date.getTimeInMillis() >= Calendar.getInstance(Locale.getDefault()).getTimeInMillis()){
			this.initialDate = date;
		}
	}

}
