// Todd Tingey
package edu.uwyo.toddt.contentproviderprog5.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by toddm on 10/20/2016.
 */

public class prog5SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "prog5SQLiteHelper";

    // Table column names
    public static final String KEY_DATE = "Date";
    public static final String KEY_TYPE = "CheckNum";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CAT = "Category";
    public static final String KEY_NAME = "Name";
    public static final String KEY_CATNAME = "CatName";
    public static final String KEY_ROWID = "_id";

    private static final String DATABASE_NAME = "prog5.db";
    public static final String TRANS_TABLE_NAME = "checking";
    public static final String CAT_TABLE_NAME = "category";
    private static final int DATABASE_VERSION = 2;

    // Database creation statements for the transactions and Category tables
    private static final String TRANS_DATA_CREATE =
            "CREATE TABLE " + TRANS_TABLE_NAME + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +  //this line is required for the cursorAdapter.
                    KEY_DATE + " TEXT, " +
                    KEY_TYPE + " TEXT, " +
                    KEY_NAME + " TEXT, " +
                    KEY_AMOUNT + " REAL, " +
                    KEY_CAT + " INTEGER, " +
                    "FOREIGN KEY(" + KEY_CAT + ") REFERENCES " + CAT_TABLE_NAME + "(" + KEY_CAT + ")" +
                    " );";

    private static final String CAT_DATA_CREATE =
            "CREATE TABLE " + CAT_TABLE_NAME + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CATNAME + " TEXT )";

    public prog5SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CAT_DATA_CREATE);
        db.execSQL(TRANS_DATA_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRANS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        onCreate(db);
    }
}
