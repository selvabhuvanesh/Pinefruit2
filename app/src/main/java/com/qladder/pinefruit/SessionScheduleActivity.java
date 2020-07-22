package com.qladder.pinefruit;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SessionScheduleActivity<TimePickerFragment> extends AppCompatActivity {

    Button fromTimebtn;
    Button toTimebtn;
    Button savebtn;
    Button publishbtn;
    CalendarView sdate;
    String selectedDate;
    static int fromHour;
    static int toHour;
    static int frommin;
    static int tomin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        fromTimebtn = (Button) findViewById(R.id.fromtime);
        toTimebtn = (Button) findViewById(R.id.totime);
        savebtn = (Button) findViewById(R.id.save);
        publishbtn = (Button) findViewById(R.id.publish);
        sdate = (CalendarView) findViewById(R.id.date);


        //Code while selecting From time to go hear


        fromTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog fromDialog = new Dialog(ScheduleActivity.this);
                //showTimePickerDialog(view);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SessionScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                fromHour = i;
                                frommin = i1;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, fromHour, frommin);
                                fromTimebtn.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(fromHour, frommin);
                timePickerDialog.show();

            }
        });


        //Code while selecting to Time should go here
        toTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showTimePickerDialog(view);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SessionScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                toHour = i;
                                tomin = i1;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, toHour, tomin);
                                toTimebtn.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(toHour, tomin);
                timePickerDialog.show();


            }
        });


        //Set the date picked from the calendar


        //method for onclick of Save button
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(SessionScheduleActivity.this, "Saved Successfully", Toast.LENGTH_LONG).show();
                Intent test = getIntent();
                Toast.makeText(SessionScheduleActivity.this,"Vale of Location is : "+test.getStringExtra("Latitude"),Toast.LENGTH_LONG).show();

            }
        });

        //method for onclick of publish or share  button
        publishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mservice = "\nService : "+getIntent().getStringExtra("service");
                String mfacility = "Company : "+getIntent().getStringExtra("facility");
                String mprovider = "\nProvider : "+getIntent().getStringExtra("provider");
                String mfromtime = "\n From Time :"+fromTimebtn.getText().toString();
                String mtotime = "\nTo Time :"+ toTimebtn.getText().toString();
                String mdate = "\nDate : "+ selectedDate;

                String sessionInfo = mfacility+mservice+mprovider+mdate+mfromtime+mtotime ;

                AlertDialog confirmDialog;
                confirmDialog = new AlertDialog.Builder(SessionScheduleActivity.this)
                        .setTitle("Session Review")
                        .setMessage(sessionInfo)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent confirmIntent = new Intent(SessionScheduleActivity.this, ProviderRegitserConfirmActivity.class);
                                confirmIntent.putExtra("service", getIntent().getStringExtra("service"));
                                confirmIntent.putExtra("facility", getIntent().getStringExtra("facility"));
                                confirmIntent.putExtra("provider", getIntent().getStringExtra("provider"));
                                confirmIntent.putExtra("fromtime", fromTimebtn.getText().toString());
                                confirmIntent.putExtra("totime", toTimebtn.getText().toString());
                                confirmIntent.putExtra("sdate", selectedDate);
                                startActivity(confirmIntent);
                            }
                        })
                        .setNegativeButton("Not Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();



                /*confirmIntent.putExtra("Latitude ", mainIntent.getStringExtra("Latitude"));
                confirmIntent.putExtra("Longitude ", mainIntent.getStringExtra("Longitude"));
                confirmIntent.putExtra("AddressLine ", mainIntent.getStringExtra("AddressLine"));
                confirmIntent.putExtra("Location ", mainIntent.getStringExtra("Location"));*/
            }

        });


        sdate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //Setting the date based on selection
                selectedDate = i + "/" + (i1 + 1) + "/" + i2;

            }


        });


    }




    @Override
    protected void onResume() {
        super.onResume();
        /*String facility;
        String provider;
        String service;
        Intent homeIntent = getIntent();
        service = homeIntent.getStringExtra("service");
        provider = homeIntent.getStringExtra("provider");
        facility = homeIntent.getStringExtra("facility");

        //Log.d("Resume Called ***** ", service);
        //Toast.makeText(this, "Service Name : " + service + "\n Provider : " + provider + "\n Facility : " + facility, Toast.LENGTH_LONG).show();
        */

    }

}