package br.edu.ifpi.projetoeventos.models.event;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import br.edu.ifpi.projetoeventos.models.enums.EventStatus;
import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.others.Mappable;

public class Event extends Mappable{

	private String name;
	private Location location;
	private Event relatedEvent;
	private List<Activity> activityList = new ArrayList<>();
	private EventStatus status;
	private EventType eventType;
	private Calendar initialDate;
	private Calendar finalDate;

	Event(){
        setID(UUID.randomUUID().toString());
        setStatus(EventStatus.OPEN);
    }

    public Event(String name, Location location){
        this.name = name;
        setID(UUID.randomUUID().toString());
        setStatus(EventStatus.OPEN);
    }

	public Event(String id){
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

	public List<Activity> getActivityList() {
		return this.activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public EventStatus getStatus() {
		return this.status;
	}

    public void setStatus(EventStatus status) {
        this.status = status;
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

    public Calendar getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(int day, int month, int year) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, (month-1));
        date.set(Calendar.DAY_OF_MONTH, day);
        if(date.getTimeInMillis() >= getInitialDate().getTimeInMillis()){
            this.finalDate = date;
        }
    }

    @Exclude
	@Override
	public void fromMap(Map<String, Object> map){
		if(map == null) Log.i("Event", "Received map is null, aborting conversion");
		else{
			setID((String) map.get("ID"));
			setName((String) map.get("name"));
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis((long) map.get("initialDate"));
			this.initialDate = c;
            c.setTimeInMillis((long) map.get("finalDate"));
			this.finalDate = c;
			setStatus(EventStatus.valueOf((String) map.get("status")));
//			setRelatedEvent(new Event((String) map.get("relatedEvent")));
//			setLocation(new Location((String) map.get("location")));

			//

//			Map<String, Object> relatedEvents = (Map<String, Object>) map.get("related");
//			if(relatedEvents != null && !relatedEvents.isEmpty()){
//				int size = Integer.valueOf(String.valueOf(relatedEvents.get("size")));
//				for (int i = 0; i < size; i++) {
//					getRelatedEvents().add(new Event((String) relatedEvents.get(String.valueOf(i))));
//				}
//			}
//
//			Map<String, Object> activities = (Map<String, Object>) map.get("activities");
//			if(activities != null && !activities.isEmpty()){
//				int size = Integer.valueOf(String.valueOf(activities.get("size")));
//				for (int i = 0; i < size; i++) {
//					getActivities().add(new Activity((String) activities.get(String.valueOf(i))));
//				}
//			}
//
//			Map<String, Object> participants = (Map<String, Object>) map.get("participants");
//			if(participants != null && !participants.isEmpty()){
//				int size = Integer.valueOf(String.valueOf(participants.get("size")));
//				for (int i = 0; i < size; i++) {
//					getParticipants().add(new Participant((String) participants.get(String.valueOf(i))));
//				}
//			}
//
//			Map<String, Object> entries = (Map<String, Object>) map.get("entries");
//			if(entries != null && !entries.isEmpty()){
//				int size = Integer.valueOf(String.valueOf(entries.get("size")));
//				for (int i = 0; i < size; i++) {
//					getEntries().add(new Entry((String) entries.get(String.valueOf(i))));
//				}
//			}
//
		}
	}

	@Override
	@Exclude
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("ID", getID());
		result.put("name", getName());
		result.put("initialDate", getInitialDate().getTimeInMillis());
		result.put("endingDate", getFinalDate().getTimeInMillis());
        result.put("status", getStatus().name());
//		result.put("relatedEvent", getRelatedEvent().getID());
//		result.put("location", getLocation().getID());

//		Map<String, Object> relatedEvents = getListMap(getRelatedEvents());

//		if(relatedEvents != null) result.put("related", relatedEvents);

//		Map<String, Object> activities = Activity.getListMap(getActivityList());

//		if(activities != null) result.put("activities", activities);

//		Map<String, Object> participant = Participant.getListMap(getParticipants());

//		if(participant != null) result.put("participants", participant);

//		Map<String, Object> entries = Entry.getListMap(getEntries());

//		if(entries != null) result.put("entries", entries);

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
