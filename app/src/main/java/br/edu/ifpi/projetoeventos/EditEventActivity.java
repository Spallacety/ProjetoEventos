package br.edu.ifpi.projetoeventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.firebase.EventDAO;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.others.MyActivity;

public class EditEventActivity extends MyActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = (Event) getIntent().getSerializableExtra("event");
        } else {
            event = Factory.makeEvent();
            getSupportActionBar().setTitle(R.string.new_event);
        }
    }

    public void openActivityListScreen(View v){
        Intent intent = new Intent(this, ActivityListActivity.class);
        if(event.getActivityList() != new ArrayList<Activity>()){
            intent.putExtra("activityList", event.getActivityList());
        }
        startActivity(intent);
    }
    
    public void openLocationScreen(View v){
        Intent intent = new Intent(this, EditLocationActivity.class);
        if(event.getLocation() != null){
            intent.putExtra("location", event.getLocation());
        }
        startActivity(intent);
    }

    public void saveEvent(View v){

        EventDAO.getInstance().save(event);
    }
}