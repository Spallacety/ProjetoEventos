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

    public ArrayList<Event> getAll(){
        final ArrayList<Event> result = new ArrayList<>();
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

    public Location getLocation(final @NonNull Event event){
        Location result = LocationDAO.getInstance().findById(event.getLocation().getID());
        if(result != null) return result;
        return null;
    }

    public ArrayList<Activity> getActivityList(final @NonNull Event event){
        ArrayList<Activity> activityList = ActivityDAO.getInstance().getAll();
        ArrayList<Activity> result = new ArrayList<>();

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

    public Event findById(final @NonNull String id) {
        List<Event> list = getAll();
        Event result = Factory.makeEvent();
            for (Event event : list) {
                if(event.getID().equals(id)){
                    event.setLocation(getLocation(event));
                    event.setActivityList(getActivityList(event));
                    result = event;
                }
                break;
            }

        return result;
    }

}