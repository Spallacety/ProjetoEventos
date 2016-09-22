package br.edu.ifpi.projetoeventos;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import java.util.Calendar;

import br.edu.ifpi.projetoeventos.models.others.MyActivity;
import butterknife.BindView;
import butterknife.OnClick;

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
        final Calendar c = Calendar.getInstance();
        initialHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LocalTime newInitialTime = LocalTime.of(hourOfDay, minute);
                        if (finalTime == null) {
                            initialTime = newInitialTime;
                            initialHour.setText(initialTime.toString());
                        } else if (newInitialTime.isBefore(finalTime)){
                            initialTime = newInitialTime;
                            initialHour.setText(initialTime.toString());
                        } else {
                            Toast.makeText(getBaseContext(), EditActivityActivity.this.getString(R.string.initial_hour_before_final), Toast.LENGTH_LONG).show();
                        }
                    }
                };
                TimePickerDialog dlg = new TimePickerDialog(EditActivityActivity.this, otsl, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
                if(initialTime != null) dlg.updateTime(initialTime.getHour(), initialTime.getMinute());
                dlg.show();
            }
        });
        finalHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LocalTime newFinalTime = LocalTime.of(hourOfDay, minute);
                        if (initialTime == null){
                            finalTime = newFinalTime;
                            finalHour.setText(finalTime.toString());
                        } else if (newFinalTime.isAfter(initialTime)) {
                            finalTime = newFinalTime;
                            finalHour.setText(finalTime.toString());
                        } else {
                            Toast.makeText(getBaseContext(), EditActivityActivity.this.getString(R.string.final_hour_after_initial), Toast.LENGTH_LONG).show();
                        }
                    }
                };
                TimePickerDialog dlg = new TimePickerDialog(EditActivityActivity.this, otsl, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
                if(finalTime != null) dlg.updateTime(finalTime.getHour(), finalTime.getMinute());
                dlg.show();
            }
        });
    }
}