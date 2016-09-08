package br.edu.ifpi.projetoeventos;

import android.os.Bundle;

import br.edu.ifpi.projetoeventos.models.others.MyActivity;

public class EventActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }
}
