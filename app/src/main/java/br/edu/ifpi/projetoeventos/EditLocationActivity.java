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
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.Location;
import br.edu.ifpi.projetoeventos.models.others.MyActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditLocationActivity extends MyActivity {

    @BindView(R.id.location_edit_description) EditText description;
    @BindView(R.id.location_edit_coordinates) Button coordinates;
    @BindView(R.id.location_edit_type) Spinner types;
    @BindView(R.id.location_edit_save) Button save;

    public final static int PICK_PLACE = 2;
    private Location location;
    private List<String> translatedTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
        ButterKnife.bind(this);

        location = Factory.makeLocation();

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location.setDescription(description.getText().toString());
                location.setLocationType(LocationType.fromTranslatedName(getApplicationContext(), translatedTypes.get(types.getSelectedItemPosition())));
                LocationDAO.getInstance().save(location);
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
}
