package br.edu.ifpi.projetoeventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

import br.edu.ifpi.projetoeventos.firebase.LocationDAO;
import br.edu.ifpi.projetoeventos.models.enums.LocationType;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.Location;
import br.edu.ifpi.projetoeventos.models.others.MyActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditLocationActivity extends MyActivity {

    public final static int PICK_PLACE = 2;
    public final static int ADD_NEW_LOCATION = 256;

    @BindView(R.id.location_edit_description) EditText description;
    @BindView(R.id.location_edit_coordinates) Button coordinates;
    @BindView(R.id.location_edit_type) Spinner types;
    @BindView(R.id.location_edit_save) Button save;

    private Location location;
    private List<String> translatedTypes;
    private Event editEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
        ButterKnife.bind(this);

        editEvent = (Event) getIntent().getSerializableExtra("event");

        if(editEvent.getLocation() != null){
            location =  editEvent.getLocation();
        }

        else location = Factory.makeLocation();

        hideKeyboard(description);
    }

    @Override
    protected void onResume() {
        super.onResume();

        coordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(EditLocationActivity.this), PICK_PLACE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        translatedTypes = LocationType.getPossibleRelatedLocations(this, LocationType.NONE);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, translatedTypes);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(spinnerArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_PLACE: {
                    Place place = PlacePicker.getPlace(this, data);
                    location.setAddress(place.getAddress().toString());
                    location.setCoordinates(place.getLatLng());
                    this.coordinates.setText(location.getAddress());
                    break;
                }
            }
        }
    }

    private int getPositionOfType(LocationType locationType){
        int position = 0;
        for (int i = 0; i < translatedTypes.size(); i++) {
            if(locationType.getTranslatedName(getApplicationContext()).equals(translatedTypes.get(i))){position = i;}
        }
        return position;
    }

    public void saveLocation(View v){
        location.setDescription(description.getText().toString());
        location.setAddress(coordinates.getText().toString());
        location.setLocationType(LocationType.fromTranslatedName(getApplicationContext(), translatedTypes.get(types.getSelectedItemPosition())));
        Intent result = new Intent();
        result.putExtra("location", location);
        setResult(RESULT_OK, result);
        finish();
    }
}
