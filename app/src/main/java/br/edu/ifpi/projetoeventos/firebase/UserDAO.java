package br.edu.ifpi.projetoeventos.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.User;

public class UserDAO {

    private final static String KEY_NAME = "users";

    private static UserDAO instance;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (instance == null) instance = new UserDAO();
        return instance;
    }

    public interface LoadListCallback {
        void onListLoaded(List<User> list);
    }

    public interface LoadOneCallback {
        void onLoaded(User user);
    }

    public void save(final @NonNull User user) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(user.getID()).setValue(user.toMap());
    }

    public void delete(final @NonNull User user) {
        FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(user.getID()).removeValue();
    }

    public void getAll(final @NonNull LoadListCallback callback) {
        final List<User> result = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
            FirebaseDatabase.getInstance().getReference().child(KEY_NAME).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                HashMap<String, Object> map = (HashMap<String, Object>) child.getValue();
                                if (map != null) {
                                    User user = Factory.makeUser();
                                    user.fromMap(map);
                                    if (!contains(result, user)) {
                                        result.add(user);
                                    }
                                }
                            }
                            callback.onListLoaded(result);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onListLoaded(result);
                    }
                });
            }
        }).start();
    }

    public boolean contains(List<User> list, User user) {
        for (User u : list) {
            if (u.getID().equals(user.getID())) {
                return true;
            }
        }
        return false;
    }

    public void findById(final @NonNull String id, final @NonNull LoadOneCallback callback) {
//        getAll(new LoadListCallback() {
//            @Override
//            public void onListLoaded(List<User> list) {
//                for (User user : list) {
//                    if (user.getID().equals(id)) {
//                        callback.onLoaded(user);
//                    }
//                    break;
//                }
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child(KEY_NAME).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                        if(map != null) {
                            User user = Factory.makeUser();
                            user.fromMap(map);
                            callback.onLoaded(user);
                        }else{
                            callback.onLoaded(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onLoaded(null);
                    }
                });
            }
        }).start();
    }

}
