package br.edu.ifpi.projetoeventos.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.Location;

public class LocationDAO {

    private final static String KEY_NAME = "locations";

    private static LocationDAO instance;

    private LocationDAO() {}

    public static LocationDAO getInstance(){
        if(instance == null) instance = new LocationDAO();
        return instance;
    }

    public void save(final @NonNull Location location) {
        final Map<String, Object> mapToSave = location.toMap();
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(location.getID()).setValue(mapToSave);
    }

    public void delete(final @NonNull Location location) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(location.getID()).removeValue();
    }

    public List<Location> getAll() {
        final List<Location> result = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child(KEY_NAME).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                HashMap<String, Object> map = (HashMap<String, Object>) child.getValue();
                                if(map != null){
                                    Location location = Factory.makeLocation();
                                    location.fromMap(map);
                                    result.add(location);
                                }
                            }
                        }
                        if(!result.isEmpty()){
                            for (int i = 0; i < result.size(); i++) {
                                result.set(i, getRelatedLocations(result, result.get(i)));
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

    public Location findById(final @NonNull String id) {
        Location result = Factory.makeLocation();
        List<Location> list = getAll();
        for (Location location : list) {
            if(location.getID().equals(id)){
                result = getRelatedLocations(list, location);
            }
        }
        return result;
    }

    public Location getRelatedLocations(List<Location> list, Location parent){
        for (int i = 0; i < parent.getRelatedLocations().size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if(parent.getRelatedLocations().get(i).getID().equals(list.get(j).getID())){
                    parent.getRelatedLocations().set(i, list.get(j));
                    break;
                }
            }
        }
        return parent;
    }

}