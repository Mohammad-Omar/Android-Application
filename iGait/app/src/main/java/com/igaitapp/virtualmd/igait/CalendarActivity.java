package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    public final static String EXTRA_PATIENT_CALENDAR = " com.igaitapp.virtualmd.igait.PATIENT_CALENDAR";
    public final static String EXTRA_SELECTED_DATE_CALENDAR = " com.igaitapp.virtualmd.igait.SELECTED_DATE_CALENDAR";

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();

        patient = (Patient) intent.getSerializableExtra(MainActivity.EXTRA_PATIENT_MAIN);

        populateListViewPatient();
        populateCalendarViewHealth();
    }

    private void populateListViewPatient () {
        ListView list;
        PatientListAdapter adapter;
        List<Patient> patientList = new ArrayList<>();

        patientList.add(patient);

        list = (ListView) findViewById(R.id.listViewPatientCalendar);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);
    }

    private void populateCalendarViewHealth() {
        CalendarView calendar;

        calendar = (CalendarView) findViewById(R.id.calendarViewHealth);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Intent intent;
                String selectedDate;

                intent = new Intent(CalendarActivity.this, EventActivity.class);

                selectedDate = day + " " + (month + 1) + " " + year;

                intent.putExtra(EXTRA_PATIENT_CALENDAR, patient);
                intent.putExtra(EXTRA_SELECTED_DATE_CALENDAR, selectedDate);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
