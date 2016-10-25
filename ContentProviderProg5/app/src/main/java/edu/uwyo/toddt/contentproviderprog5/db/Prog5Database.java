package edu.uwyo.toddt.contentproviderprog5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.Arrays;

/**
 * Created by toddm on 10/20/2016.
 */

public class Prog5Database {

    private prog5SQLiteHelper DBHelper;
    private SQLiteDatabase db;

    // Constructor
    public Prog5Database(Context context){
        DBHelper = new prog5SQLiteHelper(context);
    }

    //----Open Database----
    public void open() throws SQLException {
        db = DBHelper.getWritableDatabase();
    }

    // True if Database is open
    public boolean isOpen() throws SQLException {
        return db.isOpen();
    }

    //----Close Database----
    public void close(){
        DBHelper.close();
        db.close();
    }


    //-------Inserts--------
    public long insertTrans(String date, String type, String name, String amount, String category){
        ContentValues initialValues = new ContentValues();
        initialValues.put(prog5SQLiteHelper.KEY_DATE, date);
        initialValues.put(prog5SQLiteHelper.KEY_TYPE, type);
        initialValues.put(prog5SQLiteHelper.KEY_NAME, name);
        initialValues.put(prog5SQLiteHelper.KEY_AMOUNT, amount);
        initialValues.put(prog5SQLiteHelper.KEY_CAT, category);
        return db.insert(prog5SQLiteHelper.TRANS_TABLE_NAME, null, initialValues);
    }
    public long insertCat(String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(prog5SQLiteHelper.KEY_CATNAME, name);
        return db.insert(prog5SQLiteHelper.CAT_TABLE_NAME, null, initialValues);
    }
    public long cpInsert(String TableName, ContentValues values) {
        return db.insert(TableName, null, values);
    }


    //------Queries-------

    // Get all the rows
    public Cursor getAllTrans() {
        Cursor transC = cpQuery(prog5SQLiteHelper.TRANS_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_ROWID, prog5SQLiteHelper.KEY_DATE, prog5SQLiteHelper.KEY_TYPE, prog5SQLiteHelper.KEY_NAME, prog5SQLiteHelper.KEY_AMOUNT, prog5SQLiteHelper.KEY_CAT},
                null,
                null,
                prog5SQLiteHelper.KEY_ROWID); // sort by row ID
        if(transC != null){
            transC.moveToFirst();
        }
        return transC;
    }

    public Cursor getAllCat() {
        Cursor catC = cpQuery(prog5SQLiteHelper.CAT_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_ROWID, prog5SQLiteHelper.KEY_CATNAME},
                null,
                null,
                prog5SQLiteHelper.KEY_ROWID); // sort by row ID
        if(catC != null){
            catC.moveToFirst();
        }
        return catC;
    }

    // Get one entry
    public Cursor getTransDate(String date){
        Cursor mCursor = cpQuery(prog5SQLiteHelper.TRANS_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_DATE, prog5SQLiteHelper.KEY_TYPE, prog5SQLiteHelper.KEY_NAME, prog5SQLiteHelper.KEY_AMOUNT, prog5SQLiteHelper.KEY_CAT},
                prog5SQLiteHelper.KEY_DATE + "=\'" + date + "\'",
                null,
                prog5SQLiteHelper.KEY_DATE
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getTransType(String type){
        Cursor mCursor = cpQuery(prog5SQLiteHelper.TRANS_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_DATE, prog5SQLiteHelper.KEY_TYPE, prog5SQLiteHelper.KEY_NAME, prog5SQLiteHelper.KEY_AMOUNT, prog5SQLiteHelper.KEY_CAT},
                prog5SQLiteHelper.KEY_TYPE + "=\'" + type + "\'",
                null,
                prog5SQLiteHelper.KEY_TYPE
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getTransName(String name){
        Cursor mCursor = cpQuery(prog5SQLiteHelper.TRANS_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_DATE, prog5SQLiteHelper.KEY_TYPE, prog5SQLiteHelper.KEY_NAME, prog5SQLiteHelper.KEY_AMOUNT, prog5SQLiteHelper.KEY_CAT},
                prog5SQLiteHelper.KEY_NAME + "=\'" + name + "\'",
                null,
                prog5SQLiteHelper.KEY_NAME
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getTransAmount(String amount){
        Cursor mCursor = cpQuery(prog5SQLiteHelper.TRANS_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_DATE, prog5SQLiteHelper.KEY_TYPE, prog5SQLiteHelper.KEY_NAME, prog5SQLiteHelper.KEY_AMOUNT, prog5SQLiteHelper.KEY_CAT},
                prog5SQLiteHelper.KEY_AMOUNT + "=\'" + amount + "\'",
                null,
                prog5SQLiteHelper.KEY_AMOUNT
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getTransCat(String category){
        Cursor mCursor = cpQuery(prog5SQLiteHelper.TRANS_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_DATE, prog5SQLiteHelper.KEY_TYPE, prog5SQLiteHelper.KEY_NAME, prog5SQLiteHelper.KEY_AMOUNT, prog5SQLiteHelper.KEY_CAT},
                prog5SQLiteHelper.KEY_CAT + "=\'" + category + "\'",
                null,
                prog5SQLiteHelper.KEY_CAT
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getCatName(String name){
        Cursor mCursor = cpQuery(prog5SQLiteHelper.CAT_TABLE_NAME,
                new String[]{prog5SQLiteHelper.KEY_CATNAME},
                prog5SQLiteHelper.KEY_CATNAME + "=\'" + name + "\'",
                null,
                prog5SQLiteHelper.KEY_CATNAME
        );

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor cpQuery(String TableName, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TableName);
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor cpQueryJoin(String TableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TableName
                + " INNER JOIN "
                + prog5SQLiteHelper.CAT_TABLE_NAME
                + " ON "
                + prog5SQLiteHelper.KEY_CAT
                + " = "
                + (prog5SQLiteHelper.CAT_TABLE_NAME + "." + prog5SQLiteHelper.KEY_ROWID)
        );
        if (projection != null) {
            //add to the selection for the KEY_CATNAME
            projection = append(projection, prog5SQLiteHelper.KEY_CATNAME);
        }
        //Cursor mCursor = db.rawQuery("select * from checking a INNER JOIN category b ON a.Category=b._ID",null)
        //using the query builder to manage the actual query at this point.
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }


    //-------Updates--------
    public int cpUpdate(String TableName, ContentValues values, String selection, String[] selectionArgs){
        return db.update(TableName, values, selection, selectionArgs);
    }


    //-------Deletes--------
    public int cpDelete(String TableName, String selection, String[] selectionArgs){
        return db.delete(TableName, selection, selectionArgs);
    }

    // Delete everything
    public void emptyTables(){
        db.delete(prog5SQLiteHelper.TRANS_TABLE_NAME, null, null);
        db.delete(prog5SQLiteHelper.CAT_TABLE_NAME, null, null);
    }
}
