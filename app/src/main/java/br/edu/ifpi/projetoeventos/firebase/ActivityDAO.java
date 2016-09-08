package br.edu.ifpi.projetoeventos.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.Location;

public class ActivityDAO {

    private final static String KEY_NAME = "activities";

    private static ActivityDAO instance;

    private ActivityDAO() {}

    public static ActivityDAO getInstance(){
        if(instance == null) instance = new ActivityDAO();
        return instance;
    }

    public void save(final @NonNull Activity activity) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(activity.getID()).setValue(activity.toMap());
    }

    public void delete(final @NonNull Activity activity) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(activity.getID()).removeValue();
    }

    public List<Activity> getAll() {
        final List<Activity> result = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child(KEY_NAME).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                HashMap<String, Object> map = (HashMap<String, Object>) child.getValue();
                                Activity activity = Factory.makeActivity();
                                activity.fromMap(map);
                                try{
                                    activity.setLocation(LocationDAO.getInstance().findById(activity.getLocation().getID()));
                                } catch (Exception e){}
                                if(!contains(result, activity)) result.add(activity);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }).start();

        return result;
    }

    public Activity findById(final @NonNull String id){
        List<Activity> list = getAll();
        Activity result = Factory.makeActivity();
        for (Activity activity : list) {
            if(activity.getID().equals(id)) {
                result = activity;
                break;
            }
        }
        return result;
    }

    public boolean contains(List<Activity> list, Activity activity){
        for (Activity a : list) {
            if(activity.getID().equals(a.getID())) return true;
        }
        return false;
    }

//    public Location getActivityLocation(final @NonNull Activity activity){
//        Location result = LocationDAO.getInstance().findById(activity.getLocation().getID());
//        if(result != null) return result;
//        return null;
//    }

    public List<Activity> getActivitiesFromEvent(@NonNull final Event event){
        final List<Activity> list = getAll();
        List<Activity> result = new ArrayList<>();

        for (Activity activity : list) {
            if(activity.getEvent().getID().equals(event.getID())){
                result.add(activity);
            }
        }

        return result;
    }

    public List<Activity> getActivitiesFromLocation(final Location location) {
        final List<Activity> list = getAll();
        List<Activity> result = new ArrayList<>();

        for (Activity activity : list) {
            if(activity.getLocation().getID().equals(location.getID())){
                result.add(activity);
            }
        }

        return result;
    }

}
