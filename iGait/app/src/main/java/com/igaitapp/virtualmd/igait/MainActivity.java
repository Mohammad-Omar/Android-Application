package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_PATIENT_MAIN = "com.igaitapp.virtualmd.igait.PATIENT_MAIN";

    private List<Patient> patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread clientThread = new Thread(new ClientThread());

        clientThread.start();
        populatePatientList();
        populateListViewPatientsMain();
    }

    private void populatePatientList() {
        List<GaitHealth> bw = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.US);
        DateFormat ex = new SimpleDateFormat("kk:mm:ss", Locale.US);

        try {
            bw.add(new GaitHealth(3, df.parse("Wed Sep 2 08:10:00 CST 2015"), df.parse("Wed Sep 2 08:11:00 CST 2015"), true));
            bw.add(new GaitHealth(3, df.parse("Wed Sep 2 12:20:00 CST 2015"), df.parse("Wed Sep 2 12:25:00 CST 2015"), false));
            bw.add(new GaitHealth(3, df.parse("Thu Sep 3 08:09:00 CST 2015"), df.parse("Thu Sep 3 08:10:00 CST 2015"), false));
            bw.add(new GaitHealth(2, df.parse("Thu Sep 3 15:00:00 CST 2015"), df.parse("Thu Sep 3 15:03:00 CST 2015"), true));
            bw.add(new GaitHealth(2, df.parse("Fri Sep 4 08:10:00 CST 2015"), df.parse("Fri Sep 4 08:14:00 CST 2015"), false));
            bw.add(new GaitHealth(1, df.parse("Fri Sep 4 12:10:00 CST 2015"), df.parse("Fri Sep 4 12:16:00 CST 2015"), true));
            bw.add(new GaitHealth(1, df.parse("Fri Sep 4 15:10:00 CST 2015"), df.parse("Fri Sep 4 15:12:00 CST 2015"), false));
            bw.add(new GaitHealth(1, df.parse("Sat Sep 5 08:10:00 CST 2015"), df.parse("Sat Sep 5 08:18:00 CST 2015"), false));
            bw.add(new GaitHealth(2, df.parse("Sat Sep 5 12:10:00 CST 2015"), df.parse("Sat Sep 5 12:19:00 CST 2015"), true));
            bw.add(new GaitHealth(3, df.parse("Sun Sep 6 08:10:00 CST 2015"), df.parse("Sun Sep 6 08:12:00 CST 2015"), false));
            bw.add(new GaitHealth(3, df.parse("Sun Sep 6 13:10:00 CST 2015"), df.parse("Sun Sep 6 13:14:00 CST 2015"), false));
            bw.add(new GaitHealth(3, df.parse("Sun Sep 6 15:10:00 CST 2015"), df.parse("Sun Sep 6 15:15:00 CST 2015"), false));
            bw.add(new GaitHealth(0, df.parse("Mon Sep 7 08:10:00 CST 2015"), df.parse("Mon Sep 7 08:11:00 CST 2015"), true));
            bw.add(new GaitHealth(0, df.parse("Mon Sep 7 13:10:00 CST 2015"), df.parse("Mon Sep 7 13:18:00 CST 2015"), true));

            patientList.add(new Patient(143256790, "Wayne", "Bruce", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("dark_knight@bmail.com", "2312 Bat Cave St.", "Gotham", "NY", 56931, "145-432-6035"), ex.parse("00:03:00"), bw, true));
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    private void populateListViewPatientsMain() {
        ListView list;
        PatientListAdapter adapter;

        list = (ListView) findViewById(R.id.listViewPatientsMain);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Patient tappedPatient;
                Intent intent;

                tappedPatient = patientList.get(position);

                intent = new Intent(MainActivity.this, CalendarActivity.class);

                intent.putExtra(EXTRA_PATIENT_MAIN, tappedPatient);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
