package br.edu.ifpi.projetoeventos.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.Location;

public class EventDAO {

    private final static String KEY_NAME = "events";

    private static EventDAO instance;

    private EventDAO() {}

    public static EventDAO getInstance(){
        if(instance == null) instance = new EventDAO();
        return instance;
    }

    public void save(final @NonNull Event event) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(event.getID()).setValue(event.toMap());

//      saveParticipants(event);

        if(event.getActivityList() != null && !event.getActivityList().isEmpty()){
            for (Activity activity : event.getActivityList()) {
                ActivityDAO.getInstance().save(activity);
            }
        }

        if(event.getLocation() != null){
            LocationDAO.getInstance().save(event.getLocation());
        }

    }

    public void delete(final @NonNull Event event) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(event.getID()).removeValue();
    }

    public List<Event> getAll(){
        final List<Event> result = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child(KEY_NAME).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                HashMap<String, Object> map = (HashMap<String, Object>) child.getValue();
                                if (map != null) {
                                    Event event = Factory.makeEvent();
                                    event.fromMap(map);
                                    if(!contains(result, event)) {result.add(event);}
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        }).start();

        return result;
    }

    public boolean contains(List<Event> list, Event event){
        for (Event e : list) {
            if(e.getID().equals(event.getID())){
                return true;
            }
        }
        return false;
    }

//    public Event retrieveRelatedEvents(Event event, List<Event> list){
//        for (int i = 0; i < event.getRelatedEvents().size(); i++) {
//            for (int j = 0; j < list.size(); j++) {
//                if(event.getRelatedEvents().get(i).getID().equals(list.get(j).getID())){
//                    event.getRelatedEvents().set(i, list.get(j));
//                    break;
//                }
//            }
//        }
//        return event;
//    }

    public Location getLocation(final @NonNull Event event){
        Location result = LocationDAO.getInstance().findById(event.getLocation().getID());
        if(result != null) return result;
        return null;
    }

    public List<Activity> getActivities(final @NonNull Event event){
        List<Activity> activityList = ActivityDAO.getInstance().getAll();
        List<Activity> result = new ArrayList<>();

        for (int i = 0; i < event.getActivityList().size(); i++) {
            for (int j = 0; j < activityList.size(); j++) {
                if(event.getActivityList().get(i).getID().equals(activityList.get(j).getID())){
                    result.add(activityList.get(j));
                    break;
                }
            }
        }

        return result;
    }

//    public void retrieveParticipants(final @NonNull Event event, final @NonNull LoadOneCallback callback){
//        ParticipantRepository.getInstance().findAll(new ParticipantDAO.LoadListCallback() {
//            @Override
//            public void onListLoaded(List<Participant> list) {
//                for (int i = 0; i < event.getParticipants().size(); i++) {
//                    for (int j = 0; j < list.size(); j++) {
//                        if(event.getParticipants().get(i).getID().equals(list.get(j).getID())){
//                            event.getParticipants().set(i, list.get(j));
//                            break;
//                        }
//                    }
//                }
//                callback.onLoaded(event);
//            }
//        });
//    }

    public Event findById(final @NonNull String id) {
        List<Event> list = getAll();
        Event result = Factory.makeEvent();
            for (Event event : list) {
                if(event.getID().equals(id)){
                    event.setLocation(getLocation(event));
                    event.setActivityList(getActivities(event));
//                    retrieveParticipants(event);
//                    event = retrieveRelatedEvents(event, list);
                    result = event;
                }
                break;
            }

        return result;
    }

//    public void saveParticipants(Event event){
//        if(event.getParticipants() != null && !event.getParticipants().isEmpty()){
//            for (Participant participant : event.getParticipants()) {
//                ParticipantRepository.getInstance().save(participant);
//            }
//        }
//    }

}