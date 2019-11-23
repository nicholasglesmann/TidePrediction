package com.glesmannn.tideprediction_nicholasglesmann;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<TideItem> tideItems;
    static final String DATE = "date";
    static final String DAY = "day";
    static final String TIDE = "tide";
    static final String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dal dal = new Dal(getApplicationContext());
        tideItems = dal.parseXmlFile("newport-or-tide-data.xml");

        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        for (TideItem item : tideItems) {
            HashMap<String, String> map = new HashMap<>();
            map.put(DATE, item.getDate());
            map.put(DAY, item.getDay());
            map.put(TIDE, item.getHighlow());
            map.put(TIME, item.getTime());

            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.listview_items,
                new String[]{DATE, DAY, TIDE, TIME},
                new int[]{R.id.dateTextView, R.id.dayTextView, R.id.tideTextView, R.id.timeTextView}
                );

        ListView itemsListView = findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(this);
        itemsListView.setFastScrollEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TideItem item = tideItems.get(position);
        Toast.makeText(this, "Height: " + item.getPredInCm() + "cm", Toast.LENGTH_SHORT).show();
    }
}
