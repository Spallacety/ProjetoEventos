package br.edu.ifpi.projetoeventos.models.event;

import com.google.firebase.database.Exclude;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.others.Mappable;

public class Activity extends Mappable implements Serializable{

	protected BigDecimal value;
	protected String name;
	protected Event event;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
    private Location location;
	private ActivityType activityType;
    private boolean obrigatory = false;
    private String responsible;
    private String responsibleBio;

    Activity(){
        setID(UUID.randomUUID().toString());
        setValue(BigDecimal.ZERO);
    }

    Activity(String id){
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

	private void setStartTime(LocalDateTime time) {
        if(time.isAfter(LocalDateTime.now())) this.startTime = time;
	}

	private void setEndTime(LocalDateTime time) {
        if(time.isAfter(getStartTime())) this.endTime = time;
	}

    public boolean isObrigatory() {
        return obrigatory;
    }

    public void setObrigatory(boolean obrigatory) {
        this.obrigatory = obrigatory;
    }

    public void setResponsibleData(String responsible, String bio){
        this.responsible = responsible;
        this.responsibleBio = bio;
    }

    @Override
    @Exclude
    public void fromMap(Map<String, Object> map)  {
        if(map != null) {
            setID((String) map.get("ID"));
            setName((String) map.get("name"));
            setStartTime(LocalDateTime.ofEpochSecond((long) map.get("startTime"), 0, ZoneOffset.UTC));
            setEndTime(LocalDateTime.ofEpochSecond((long) map.get("endTime"), 0, ZoneOffset.UTC));
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
        result.put("startTime", getStartTime().toEpochSecond(ZoneOffset.UTC));
        result.put("endTime", getEndTime().toEpochSecond(ZoneOffset.UTC));
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