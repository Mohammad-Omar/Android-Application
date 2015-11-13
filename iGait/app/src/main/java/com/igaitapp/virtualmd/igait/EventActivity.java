package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private Patient patient;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        DateFormat dateFormatDate = new SimpleDateFormat("d M yyyy"), dateFormatString = new SimpleDateFormat("EEE, MMM dd yyyy");
        Date titleAsDate;
        String titleAsString;

        patient = (Patient) intent.getSerializableExtra(CalendarActivity.EXTRA_PATIENT_CALENDAR);
        selectedDate = (String) intent.getStringExtra(CalendarActivity.EXTRA_SELECTED_DATE_CALENDAR);

        try {
            titleAsDate = dateFormatDate.parse(selectedDate);
            titleAsString = dateFormatString.format(titleAsDate);
            setTitle(titleAsString);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }

        populateListViewPatient();
        populateListViewEvents();
    }

    private void populateListViewPatient () {
        ListView list;
        PatientListAdapter adapter;
        List<Patient> patientList = new ArrayList<>();

        patientList.add(patient);

        list = (ListView) findViewById(R.id.listViewPatientEvent);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);
    }

    private void populateListViewEvents () {
        ListView list;
        EventListAdapter adapter;
        List<GaitHealth> gaitHealthList, eventList = new ArrayList<>();
        GaitHealth gaitHealth;
        SimpleDateFormat timeFormat = new SimpleDateFormat("d M yyyy");

        gaitHealthList = patient.getGaitHealth();

        for (int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = (GaitHealth) gaitHealthList.get(i);

            if (timeFormat.format(gaitHealth.getStartTime()).equals(selectedDate)) {
                eventList.add(gaitHealth);
            }
        }

        list = (ListView) findViewById(R.id.listViewEvents);
        adapter = new EventListAdapter(this, eventList);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
