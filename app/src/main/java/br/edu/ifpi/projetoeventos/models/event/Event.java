package br.edu.ifpi.projetoeventos.models.event;

import com.google.firebase.database.Exclude;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.edu.ifpi.projetoeventos.models.enums.EventStatus;
import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.others.Mappable;

public class Event extends Mappable implements Serializable {

	private String name;
	private Location location;
	private Event parentEvent;
	private ArrayList<Activity> activityList = new ArrayList<>();
	private EventStatus status;
	private EventType eventType;
	private LocalDateTime initialDate;
	private LocalDateTime finalDate;
	private List<User> team = new ArrayList<>();

	Event(){
        setID(UUID.randomUUID().toString());
        setStatus(EventStatus.OPEN);
    }

	Event(String id){
		setID(id);
		setStatus(EventStatus.OPEN);
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ArrayList<Activity> getActivityList() {
		return this.activityList;
	}

	public void setActivityList(ArrayList<Activity> activityList) {
		this.activityList = activityList;
	}

	public EventStatus getStatus() {
		return this.status;
	}

    public void setStatus(EventStatus status) {
        this.status = status;
    }

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getInitialDate() {
		return initialDate;
	}

	public LocalDateTime getFinalDate() {
		return finalDate;
	}

	public void setInitialDate(LocalDateTime date) {
		if(date.isAfter(LocalDateTime.now())) this.initialDate = date;
	}

	public void setFinalDate(LocalDateTime date) {
		if(date.isAfter(getInitialDate())) this.finalDate = date;
	}

	public Event getParentEvent() {
		return parentEvent;
	}

	public void setParentEvent(Event parentEvent) {
		this.parentEvent = parentEvent;
	}

	public List<User> getTeam() {
		return team;
	}

	public void setTeam(List<User> team) {
		this.team = team;
	}

	@Exclude
	@Override
	public void fromMap(Map<String, Object> map){
		if(map != null) {
			setID((String) map.get("ID"));
			setName((String) map.get("name"));
			setInitialDate(LocalDateTime.ofEpochSecond((long) map.get("initialDate"), 0, ZoneOffset.UTC));
			setFinalDate(LocalDateTime.ofEpochSecond((long) map.get("finalDate"), 0, ZoneOffset.UTC));
			setStatus(EventStatus.valueOf((String) map.get("status")));
			setParentEvent(new Event((String) map.get("parentEvent")));
			setLocation(new Location((String) map.get("location")));

			Map<String, Object> activityList = (Map<String, Object>) map.get("activityList");
			if(activityList != null && !activityList.isEmpty()){
				int size = Integer.valueOf(String.valueOf(activityList.get("size")));
				for (int i = 0; i < size; i++) {
					getActivityList().add(new Activity((String) activityList.get(String.valueOf(i))));
				}
			}

			Map<String, Object> team = (Map<String, Object>) map.get("team");
			if(team != null && !team.isEmpty()){
				int size = Integer.valueOf(String.valueOf(team.get("size")));
				for (int i = 0; i < size; i++) {
					getTeam().add(new User((String) team.get(String.valueOf(i))));
				}
			}
		}
	}

	@Override
	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("ID", getID());
		result.put("name", getName());
		result.put("initialDate", getInitialDate().toEpochSecond(ZoneOffset.UTC));
		result.put("finalDate", getFinalDate().toEpochSecond(ZoneOffset.UTC));
        result.put("status", getStatus().name());
		result.put("parentEvent", getParentEvent().getID());
		result.put("location", getLocation().getID());

		Map<String, Object> activityList = Activity.getListMap(getActivityList());
		if(activityList != null) result.put("activityList", activityList);

		Map<String, Object> team = User.getListMap(getTeam());
		if(team != null) result.put("team", team);

		return result;
	}

	public static Map<String, Object> getListMap(List<Event> list){
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
