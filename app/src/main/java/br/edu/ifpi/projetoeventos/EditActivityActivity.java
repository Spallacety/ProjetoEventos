package br.edu.ifpi.projetoeventos;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import br.edu.ifpi.projetoeventos.models.others.MyActivity;
import butterknife.BindView;

public class EditActivityActivity extends MyActivity {

    @BindView(R.id.initial_hour) Button initialHour;
    @BindView(R.id.final_hour) Button finalHour;

    LocalTime initialTime;
    LocalTime finalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialTime = LocalTime.now();
        finalTime = LocalTime.now();
        initialHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        initialHour.setText(initialTime.toString());
                        initialTime = LocalTime.of(hourOfDay, minute);
                    }
                };
                TimePickerDialog dlg = new TimePickerDialog(EditActivityActivity.this, otsl, initialTime.getHour(), initialTime.getMinute(), true);
                dlg.updateTime(initialTime.getHour(), initialTime.getMinute());
                dlg.show();
            }
        });
        finalHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        finalHour.setText(finalTime.toString());
                        finalTime = LocalTime.of(hourOfDay, minute);
                    }
                };
                TimePickerDialog dlg = new TimePickerDialog(EditActivityActivity.this, otsl, finalTime.getHour(), finalTime.getMinute(), true);
                dlg.updateTime(finalTime.getHour(), finalTime.getMinute());
                dlg.show();
            }
        });
    }
}