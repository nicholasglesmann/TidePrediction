package com.glesmannn.tideprediction_nicholasglesmann;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.glesmannn.tideprediction_nicholasglesmann.TideSQLiteHelper.CITY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePicker.OnDateChangedListener {

    private int day = 0;
    private int month = 0;
    private int year = 0;
    private String locationSelection = "florence";

    public static final String DATE_PICKER_DAY = "day";
    public static final String DATE_PICKER_MONTH = "month";
    public static final String DATE_PICKER_YEAR = "year";
    public static final String LOCATION_SELECTION = "location_selection";

    private DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Location spinner
        Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        // Date Picker
        datePicker = findViewById(R.id.datePicker);
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();
        year = datePicker.getYear();

        // Button
        Button getTideDataButton = findViewById(R.id.getTideByIdButton);
        getTideDataButton.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                locationSelection = "florence";
                break;
            case 1:
                locationSelection = "newport";
                break;
            case 2:
                locationSelection = "reedsport";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // doesn't work with API 19 and I don't want to deal with changing it
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.getTideByIdButton) {
            String newDay;
            String newMonth;

            day = datePicker.getDayOfMonth();
            if(day < 10) {
                newDay = "0" + day;
            } else {
                newDay = Integer.toString(day);
            }
            month = datePicker.getMonth() + 1;
            if(month < 10) {
                newMonth = "0" + month;
            } else {
                newMonth = Integer.toString(month);
            }
            year = datePicker.getYear();

            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(DATE_PICKER_DAY, newDay);
            intent.putExtra(DATE_PICKER_MONTH, newMonth);
            intent.putExtra(DATE_PICKER_YEAR, year);
            intent.putExtra(LOCATION_SELECTION, locationSelection);
            Toast.makeText(this, "Loading data...", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}
