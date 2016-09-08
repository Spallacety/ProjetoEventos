package br.edu.ifpi.projetoeventos.models.event;

import com.google.firebase.database.Exclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.others.Mappable;

public class Activity extends Mappable{

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

    Activity(){
        setID(UUID.randomUUID().toString());
        setValue(BigDecimal.ZERO);
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

    public Activity(String id){
        setID(id);
    }

	public BigDecimal getValue() {
		return value;
	}

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
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

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
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

    public void setObrigatory(boolean obrigatory) {
        this.obrigatory = obrigatory;
    }

    @Override
    @Exclude
    public void fromMap(Map<String, Object> map)  {
        if(map != null) {
            setID((String) map.get("ID"));
            setName((String) map.get("name"));
            setValue(BigDecimal.valueOf(Double.valueOf(String.valueOf(map.get("price")))));
            Calendar c = Calendar.getInstance();
			c.setTimeInMillis((long) map.get("startTime"));
            this.startTime = c;
            c.setTimeInMillis((long) map.get("endTime"));
            this.endTime = c;
            setActivityType(ActivityType.fromName((String) map.get("type")));
            this.event = new Event((String) map.get("event"));
            this.location = new Location((String) map.get("location"));
        }

    }

    @Exclude
    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", getID());
        result.put("name", getName());
        result.put("price", String.valueOf(getValue().doubleValue()));
        result.put("startDate", getStartTime().getTimeInMillis());
        result.put("endingDate", getEndTime().getTimeInMillis());
        result.put("type", getActivityType().name());
        result.put("event", getEvent().getID());
        result.put("location", getLocation().getID());
        return result;
    }

    public static Map<String, Object> getListMap(List<Activity> list){
        if(list != null && !list.isEmpty()){
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("size", list.size());
            for (int i = 0; i < list.size(); i++) {
                hashMap.put(String.valueOf(i), list.get(i).getID());
            }
            return hashMap;
        }
        return null;
    }

}