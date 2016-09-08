package br.edu.ifpi.projetoeventos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.firebase.EventDAO;
import br.edu.ifpi.projetoeventos.models.others.MyActivity;

public class EditEventActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
    }

    public void saveEvent(View v){
        EditText et = (EditText)findViewById(R.id.event_edit_name);
        Event e = new Event(et.getText().toString());
        EventDAO.getInstance().save(e);
    }
}
