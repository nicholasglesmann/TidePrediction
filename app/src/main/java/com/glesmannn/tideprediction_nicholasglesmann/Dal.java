package com.glesmannn.tideprediction_nicholasglesmann;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static com.glesmannn.tideprediction_nicholasglesmann.TideSQLiteHelper.*;



/*************** Dal class: Data Access Layer ***************/
/* The purpose of this class is to provide an API for reading
   and writing data from multiple sources. Currently it only
   reads data from files, but in the future it will also have
   methods for pulling data from an internet web service and
   for doing database operations.

   This class depends on the following classes:
   -  ParseHandler
      Used for parsing XML tide prediction data into an array of TideItem objects
   -  TideItem
      Contains fields with getters and setters for relevant tide data
 */

public class Dal {

    private Context context = null;  // Android application context--required to access the Android file system.

    // A context object should be passed to this constructor from the activity where this class is instantiated.
    public Dal(Context c)
    {
        context = c;
    }

    // This method accepts the name of a file in the assets folder as an argument
    // and returns an ArrayList of TideItem objects.
    public TideItems parseXmlFile(String fileName) {
        try {
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();
            // set content handler
            ParseHandler handler = new ParseHandler();
            xmlreader.setContentHandler(handler);
            // read the file from internal storage
            InputStream in = context.getAssets().open(fileName);
            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);
            // set the feed in the activity
            TideItems items = handler.getItems();
            return items;
        }
        catch (Exception e) {
            Log.e("Tide Predictions", e.toString());
            return null;
        }
    }

    public Cursor getTideDataByLocation(String locationSelection, String date) {

        loadTestData(locationSelection);

        // Init DB
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String query = "SELECT * FROM Tide WHERE City = ? AND Date = ?";
        String[] variables = new String[]{locationSelection, date};

        return db.rawQuery(query, variables);
    }

    public void loadTestData(String locationSelection) {

        // Initialize DB
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] variables = new String[]{locationSelection};

        // load test data
        if (db.rawQuery("SELECT * FROM Tide WHERE City = ?", variables).getCount() == 0) {
            loadDbFromXML("florence");
            loadDbFromXML("newport");
            loadDbFromXML("reedsport");
        }
    }

    private void loadDbFromXML(String locationSelection) {
        int x = 2018;
        while( x != 2021) {
            // Read XML
            String fileName = locationSelection + "-or-tide-data-" + x + ".xml";
            TideItems items = parseXmlFile(fileName);
            items.setCity(locationSelection);

            // Init DB
            TideSQLiteHelper helper = new TideSQLiteHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();

            for(TideItem item : items) {
                cv.put(CITY, items.getCity());
                cv.put(DATE, item.getDate());
                cv.put(DAY, item.getDay());
                cv.put(TIME, item.getTime());
                cv.put(PRED_IN_FT, item.getPredInFt());
                cv.put(PRED_IN_CM, item.getPredInCm());
                cv.put(HIGHLOW, item.getHighlow());
                db.insert(TIDE, null, cv);
            }
            db.close();
            x++;
        }
    }
}
