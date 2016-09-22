package br.edu.ifpi.projetoeventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.firebase.EventDAO;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.Location;
import br.edu.ifpi.projetoeventos.models.others.MyActivity;
import butterknife.BindView;

public class EditEventActivity extends MyActivity {

    private Event event;

    @BindView(R.id.event_edit_location)
    Button location;

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

    public void openActivityListScreen(View v) {
        Intent intent = new Intent(this, ActivityListActivity.class);
        if (event.getActivityList() != new ArrayList<Activity>()) {
            intent.putExtra("activityList", event.getActivityList());
        }
        startActivity(intent);
    }

    public void openLocationScreen(View v) {
        Intent intent = new Intent(this, EditLocationActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
        Event editEvent = (Event) intent.getSerializableExtra("event");
        if (editEvent.getLocation() != null)
            location.setText(editEvent.getLocation().getDescription());
    }

    public Event getEvent() {

        return event;
    }

    public void saveEvent(View v) {
        EventDAO.getInstance().save(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EditLocationActivity.ADD_NEW_LOCATION: {
                if (resultCode == RESULT_OK) {
                    Location location = (Location) data.getSerializableExtra("location");
                    this.location.setText(location.getDescription());
                    event.setLocation(location);
                }
                break;
            }
        }
    }
}