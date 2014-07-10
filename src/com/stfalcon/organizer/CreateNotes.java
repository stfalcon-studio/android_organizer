package com.stfalcon.organizer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by AlexZ on 09.07.14.
 */
public class CreateNotes extends Activity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notes);
        initViews();
    }


    /**
     *
     */
    private void initViews() {
        final EditText editTextNote = (EditText) findViewById(R.id.editText);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.time);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.date);
        final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.cb_alarm);
        timePicker.setIs24HourView(true);
        editTextNote.requestFocus();

        checkBoxAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timePicker.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                } else {
                    timePicker.setVisibility(View.GONE);
                    datePicker.setVisibility(View.GONE);
                }
            }
        });

        Button buttonCreate = (Button) findViewById(R.id.btn_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganizerApp.getInstans().saveInPreferences(editTextNote.getText().toString());
                if (checkBoxAlarm.isChecked()) {
                    setAlarm(datePicker.getDayOfMonth() + "." +
                                    (datePicker.getMonth() + 1) + "." +
                                    datePicker.getYear() + " " +
                                    timePicker.getCurrentHour() + ":" +
                                    timePicker.getCurrentMinute());
                }
                finish();
            }
        });
    }


    /**
     *
     * @param date
     */
    private void setAlarm(String date) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1
                , new Intent(getApplicationContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Log.i("Loger", date);

        long firstTime = 0;
        try {
            firstTime = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, firstTime, pendingIntent);
    }
}
