package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.constants.DefaultValueConstants;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScheduleActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private static int hourToPickup;
    private static int minuteToPickup;

    private Button pickupLater;

    private Button pickupNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        pickupLater = (Button)findViewById(R.id.laterButton);
        pickupNow = (Button)findViewById(R.id.nowButton);
        pickupNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                hourToPickup = now.get(Calendar.HOUR_OF_DAY);
                minuteToPickup = now.get(Calendar.MINUTE);
                Toast.makeText(ScheduleActivity.this,"You are scheduling pickup now",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ScheduleActivity.this,AddressActivity.class);
                intent.putExtra(DefaultValueConstants.pickupHour,hourToPickup);
                intent.putExtra(DefaultValueConstants.pickupMinute,minuteToPickup);
                startActivity(intent);
            }
        });
        pickupLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        ScheduleActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );

                //Get hour and check
                Timepoint minTimePoint;
                if(now.get(Calendar.HOUR_OF_DAY)>=9){
                    minTimePoint = new Timepoint(now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),0);
                }else{
                    minTimePoint = new Timepoint(9,0,0);
                }
                tpd.setMinTime(minTimePoint);
                tpd.setMaxTime(21,0,0);
                tpd.show(getFragmentManager(), "Pick your time");
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        hourToPickup = hourOfDay;
        minuteToPickup = minute;
        Toast.makeText(this,"You are scheduling pickup at "+hourToPickup+"hrs:"+minuteToPickup+"m ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ScheduleActivity.this,AddressActivity.class);
        intent.putExtra(DefaultValueConstants.pickupHour,hourToPickup);
        intent.putExtra(DefaultValueConstants.pickupMinute,minuteToPickup);
        startActivity(intent);
    }
}
