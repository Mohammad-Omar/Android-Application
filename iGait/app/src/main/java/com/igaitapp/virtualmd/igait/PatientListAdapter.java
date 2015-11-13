package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jesus on 8/21/15.
 */
public class PatientListAdapter extends BaseAdapter {
    private Context mContext;
    private Resources mResource;

    private List<Patient> patientList;

    PatientListAdapter(Context context, List<Patient> patientList)
    {
        this.mContext = context;
        this.mResource = mContext.getResources();

        this.patientList = patientList;
    }

    @Override
    public int getCount()
    {
        return patientList.size();
    }

    @Override
    public Patient getItem(int position)
    {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater;
        View view;

        TextView[] textViewG = new TextView[4];
        TextView textViewInfoLeft, textViewInfoRight;
        ImageView imageViewPriorityNotif;

        Patient patient;
        Date birthday;
        List<GaitHealth> gaitHealthList;
        GaitHealth gaitHealth;
        Calendar weekPrev = Calendar.getInstance(), weekCurrent = Calendar.getInstance();
        int gaitHealthSum = 0, gaitHealthCount = 0;
        String color[] = {"#ffb7b7b7", "#ffb7b7b7", "#ffb7b7b7", "#ffb7b7b7"};

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.patient_item, null);

        textViewG[0]=  (TextView) view.findViewById(R.id.textViewG1);
        textViewG[1] =  (TextView) view.findViewById(R.id.textViewG2);
        textViewG[2] =  (TextView) view.findViewById(R.id.textViewG3);
        textViewG[3] =  (TextView) view.findViewById(R.id.textViewG4);
        textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);
        imageViewPriorityNotif = (ImageView) view.findViewById(R.id.imageViewPriorityNotif);


        patient = (Patient) getItem(position);

        gaitHealthList = patient.getGaitHealth();
        gaitHealth = (GaitHealth) gaitHealthList.get(0);
        weekPrev.setTime(gaitHealth.getStartTime());

        for (int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = gaitHealthList.get(i);
            weekCurrent.setTime(gaitHealth.getStartTime());

            if (weekPrev.get(Calendar.WEEK_OF_YEAR) != weekCurrent.get(Calendar.WEEK_OF_YEAR) || (i + 1) == gaitHealthList.size()) {
                weekPrev.setTime(gaitHealth.getStartTime());

                for (int o = 0; o < 3; o++) {
                    color[o] = color[o + 1];
                }

                if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                    color[3] = "#ff689f38";
                }
                else if(Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                    color[3] = "#ffef6c00";
                }
                else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                    color[3] = "#ffed3b3b";
                }
                else if ((gaitHealthSum / gaitHealthCount) > 0) {
                    color[3] = "#ffed3b3b";
                }
                else {
                    color[3] = "#ff000000";
                }

                gaitHealthSum = 0;
                gaitHealthCount = 0;
            }

            gaitHealthSum += gaitHealth.getHealth();
            gaitHealthCount += 1;
        }

        for (int i = 0; i < 4; i++) {
            textViewG[i].setTextColor(Color.parseColor(color[i]));
        }

        if (!patient.isPriority()) {
            imageViewPriorityNotif.setVisibility(View.INVISIBLE);
        }
        else {
            imageViewPriorityNotif.setVisibility(View.VISIBLE);
        }

        textViewInfoLeft.setText(patient.getFirstName().substring(0, 1) + ". " + patient.getLastName());
        textViewInfoRight.setText("" + ((new Date().getTime() - patient.getBirthday().getTime()) / 1000 / 60 / 60 / 24 / 365));

        return view;
    }
}