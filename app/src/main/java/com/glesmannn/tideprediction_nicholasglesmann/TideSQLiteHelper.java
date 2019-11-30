package com.glesmannn.tideprediction_nicholasglesmann;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TideSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tide.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String TIDE = "Tide";
    public static final String CITY = "City";
    public static final String DATE = "Date";
    public static final String DAY = "Day";
    public static final String TIME = "Time";
    public static final String PRED_IN_FT = "Pred_In_Ft";
    public static final String PRED_IN_CM = "Pred_In_Cm";
    public static final String HIGHLOW = "HighLow";


    private Context context = null;

    public TideSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TIDE
        + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
        + CITY + " TEXT,"
        + DATE + " TEXT,"
        + DAY + " TEXT,"
        + TIME + " TEXT,"
        + PRED_IN_FT + " TEXT,"
        + PRED_IN_CM + " TEXT,"
        + HIGHLOW + " TEXT"
        + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
