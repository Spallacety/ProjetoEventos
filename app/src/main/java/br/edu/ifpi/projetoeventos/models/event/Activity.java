package br.edu.ifpi.projetoeventos.models.event;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.location.Location;

public class Activity{

    private String id;
	protected BigDecimal value;
	protected String name;
	protected Event event;
	private Calendar startTime;
	private Calendar endTime;
    private Location location;
	private ActivityType activityType;
    private boolean obrigatory = false;

    public List<ActivityType> getObrigatoryTypes() {
        List<ActivityType> obrigatoryTypes = new ArrayList<>();
        obrigatoryTypes.add(ActivityType.COFFEEBREAK);
        obrigatoryTypes.add(ActivityType.INTERVAL);
        obrigatoryTypes.add(ActivityType.REGISTRATION);
        return obrigatoryTypes;
    }

    public Activity(String name, String value, ActivityType activityType, Calendar startTime, Calendar endTime, Location location){
		this.name = name;
		this.value = new BigDecimal(value);
		this.activityType = activityType;
		this.startTime = startTime;
		this.endTime = endTime;
        this.location = location;
        for (ActivityType t : getObrigatoryTypes()) {
            if(activityType.equals(t)){
                this.obrigatory = true;
            }
        }
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

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

	private void setStartTime(int hour, int minutes, int day, int month, int year) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minutes);
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, (month-1));
        time.set(Calendar.DAY_OF_MONTH, day);
        if(time.getTimeInMillis() >= Calendar.getInstance(Locale.getDefault()).getTimeInMillis()){
            this.startTime = time;
        }
	}

	private void setEndTime(int hour, int minutes, int day, int month, int year) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minutes);
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, (month-1));
        time.set(Calendar.DAY_OF_MONTH, day);
        if(time.getTimeInMillis() >= getStartTime().getTimeInMillis()){
            this.endTime = time;
        }
	}

    public boolean isObrigatory() {
        return obrigatory;
    }
}
