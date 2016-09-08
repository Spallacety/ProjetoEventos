package br.edu.ifpi.projetoeventos.models.event;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.edu.ifpi.projetoeventos.models.enums.LocationType;
import br.edu.ifpi.projetoeventos.models.others.Mappable;

public class Location extends Mappable{

    private LatLng coordinates;
    private LocationType locationType;
    private String description;
    private String address;

    private Location parentLocation;
    private List<Location> relatedLocations = new ArrayList<>();
    private List<Activity> relatedActivities = new ArrayList<>();

    Location(){
        setID(UUID.randomUUID().toString());
        setLocationType(LocationType.NONE);
    }

    public Location(String id){
        setID(id);
        setLocationType(LocationType.NONE);
    }

    public void addRelatedActivity(Activity activity){
        if(!getRelatedActivities().contains(activity)){
            getRelatedActivities().add(activity);
        }
    }

    public void addRelatedLocation(Location l){
        if(l != null && !getRelatedLocations().contains(l)){
            l.setParentLocation(this);
            getRelatedLocations().add(l);
        }
    }

    public List<Location> getRelatedLocations() {
        if(relatedLocations == null){relatedLocations = new ArrayList<>();}
        return relatedLocations;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getParentLocation() {
        if(parentLocation == null) return new Location("0");
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public void setParentLocationFromID(String id){
        parentLocation = new Location(id);
    }

    public List<String> getSelectableLocationsTitles(){
        List<String> list = new ArrayList<>();
        list.add(getDescription());
        if(!getRelatedLocations().isEmpty()){
            for (Location location : getRelatedLocations()) {
                list.addAll(location.getSelectableLocationsTitles());
            }
        }
        return list;
    }

    public List<Location> getSelectableLocations(){
        List<Location> list = new ArrayList<>();
        list.add(this);
        if(!getRelatedLocations().isEmpty()){
            for (Location location : getRelatedLocations()) {
                list.addAll(location.getSelectableLocations());
            }
        }
        return list;
    }

    @Override
    public void fromMap(Map<String, Object> map) {
        if(map != null){
            setID((String) map.get("ID"));
            setDescription((String) map.get("description"));
            setAddress((String) map.get("address"));
            setCoordinates(new LatLng((double) map.get("latitude"), (double) map.get("longitude")));
            setLocationType(LocationType.fromName((String) map.get("type")));
            setParentLocationFromID((String) map.get("parent"));

            Map<String, Object> relatedLocations = (Map<String, Object>) map.get("related");
            if(relatedLocations != null && !relatedLocations.isEmpty()){
                int size = Integer.valueOf(String.valueOf(relatedLocations.get("size")));
                for (int i = 0; i < size; i++) {
                    getRelatedLocations().add(new Location((String) relatedLocations.get(String.valueOf(i))));
                }
            }

            Map<String, Object> relatedActivities = (Map<String, Object>) map.get("activities");
            if(relatedActivities != null && !relatedActivities.isEmpty()){
                int size = Integer.valueOf(String.valueOf(relatedActivities.get("size")));
                for (int i = 0; i < size; i++) {
                    getRelatedActivities().add(new Activity((String) relatedActivities.get(String.valueOf(i))));
                }
            }

        }
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("ID", getID());
        map.put("description", getDescription());
        map.put("address", getAddress());
        map.put("latitude", coordinates.latitude);
        map.put("longitude", coordinates.longitude);
        map.put("type", getLocationType().name());
        map.put("parent", getParentLocation().getID());

        Map<String, Object> relatedLocations = getListMap(getRelatedLocations());
        if(relatedLocations != null) map.put("related", relatedLocations);

        Map<String, Object> activities = Activity.getListMap(getRelatedActivities());
        if(activities != null) map.put("activities", activities);

        return map;
    }

    public static Map<String, Object> getListMap(List<Location> list){
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

    public List<Activity> getRelatedActivities() {
        if(relatedActivities == null) {relatedActivities = new ArrayList<>();}
        return relatedActivities;
    }
}
