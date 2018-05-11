package com.example.shawn.whosturnmusicapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    Button recordUser;
    TextView text;
    SharedPreferences sharedPref;
    boolean lastPerson; // true = Shawn, false = Katie
    String lastPersonData;

    public static final String LAST_PERSON = "last_person";
    public static final String LAST_PERSON_DATA = "last_person_data";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordUser = findViewById(R.id.recordUser);
        text = findViewById(R.id.text);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        lastPerson = sharedPref.getBoolean(LAST_PERSON, true);
        lastPersonData = sharedPref.getString(LAST_PERSON_DATA, "");
    }

    public void onRecordUser(View view)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        // switch the last person to have had their music played in the car.
        lastPerson = !lastPerson;
        editor.putBoolean(LAST_PERSON, lastPerson);
        // get date and time for the last record;
        String cd = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());
        String name = (lastPerson) ? "Shawn" : "Katie";
        String timeStamp = cd.substring(9, 11) + ":" + cd.substring(11, 13)
                + " on " + cd.substring(4, 6) + "/" + cd.substring(6, 8);
        lastPersonData = name + " last played music at " + timeStamp;
        editor.putString(LAST_PERSON_DATA, lastPersonData);
        editor.apply();
        updateText();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        updateText();
    }

    private void updateText()
    {
        text.setText(lastPersonData);
    }
}
