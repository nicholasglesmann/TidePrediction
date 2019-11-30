package com.glesmannn.tideprediction_nicholasglesmann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.glesmannn.tideprediction_nicholasglesmann.MainActivity.DATE_PICKER_DAY;
import static com.glesmannn.tideprediction_nicholasglesmann.MainActivity.DATE_PICKER_MONTH;
import static com.glesmannn.tideprediction_nicholasglesmann.MainActivity.DATE_PICKER_YEAR;
import static com.glesmannn.tideprediction_nicholasglesmann.MainActivity.LOCATION_SELECTION;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Dal dal = new Dal(this);
    Cursor cursor = null;
    String locationSelection = "florence";
    SimpleCursorAdapter adapter = null;
    private TideItems tideItems;

    private String day;
    private String month;
    private int year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        day = intent.getExtras().getString(DATE_PICKER_DAY);
        month = intent.getExtras().getString(DATE_PICKER_MONTH);
        year = intent.getExtras().getInt(DATE_PICKER_YEAR);
        locationSelection = intent.getExtras().getString(LOCATION_SELECTION);

        // Initialize DB
        dal.loadTestData(locationSelection);

        // Get tide data from db
        String date = year + "/" + month + "/" + day;
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        cursor = dal.getTideDataByLocation(locationSelection, date);

        // Set up adapter for ListView
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.listview_items,
                cursor,
                new String[]{TideSQLiteHelper.DATE,
                        TideSQLiteHelper.DAY,
                        TideSQLiteHelper.TIME,
                        TideSQLiteHelper.HIGHLOW},
                new int[]{
                        R.id.dateTextView,
                        R.id.dayTextView,
                        R.id.timeTextView,
                        R.id.tideTextView
                },
                0 );

        ListView itemsListView = findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(this);
        itemsListView.setFastScrollEnabled(true);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //TideItem item = tideItems.get(position);
        // Toast.makeText(this, "Height: " + item.getPredInCm() + "cm", Toast.LENGTH_SHORT).show();
    }

    public void databaseMessage(){
    }

}
