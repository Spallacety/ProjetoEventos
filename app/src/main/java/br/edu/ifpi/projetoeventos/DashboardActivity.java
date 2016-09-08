package br.edu.ifpi.projetoeventos;

import android.os.Bundle;

import br.edu.ifpi.projetoeventos.models.others.MyActivity;

public class DashboardActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
}
