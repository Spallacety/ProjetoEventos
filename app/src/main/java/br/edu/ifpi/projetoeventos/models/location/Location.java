package br.edu.ifpi.projetoeventos.models.location;

import java.util.List;

import br.edu.ifpi.projetoeventos.models.enums.LocationType;
import br.edu.ifpi.projetoeventos.models.event.Activity;

public class Location{

//    private LatLng coordinates;
    private LocationType locationType;
    private String description;
    private String address;

    private Location parentLocation;
    private List<Location> relatedLocations;
    private List<Activity> relatedActivities;

}
