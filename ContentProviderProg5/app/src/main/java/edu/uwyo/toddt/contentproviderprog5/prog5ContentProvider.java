// Todd Tingey
package edu.uwyo.toddt.contentproviderprog5;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import edu.uwyo.toddt.contentproviderprog5.db.Prog5Database;
import edu.uwyo.toddt.contentproviderprog5.db.prog5SQLiteHelper;

/**
 * Created by toddm on 10/20/2016.
 */

public class prog5ContentProvider extends ContentProvider {

    // Uri's and variables
    public static final String PROVIDER_NAME = "edu.cs4730.prog4db";

    public static final Uri CONTENT_URI_trans = Uri.parse("content://" + PROVIDER_NAME + "/Accounts/transactions/1");
    public static final Uri CONTENT_URI_cat = Uri.parse("content://" + PROVIDER_NAME + "/Category");

    private static final int TRANS = 1;
    private static final int TRANS_ID = 2;
    private static final int CAT = 3;
    private static final int CAT_ID = 4;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Accounts/transactions/1", TRANS);
        uriMatcher.addURI(PROVIDER_NAME, "Accounts/transactions/1/#", TRANS_ID);
        uriMatcher.addURI(PROVIDER_NAME, "Category", CAT);
        uriMatcher.addURI(PROVIDER_NAME, "Category/#", CAT_ID);
    }

    static final String TAG = "prog5DBCP";

    // The database to use
    Prog5Database db;


    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            case TRANS:
                return "vnd.android.cursor.dir/vnd.cs4730.Accounts/transactions/1";
            case TRANS_ID:
                return "vnd.android.cursor.item/vnd.cs4730.Accounts/transactions/1";
            case CAT:
                return "vnd.android.cursor.dir/vnd.cs4730.Category";
            case CAT_ID:
                return "vnd.android.cursor.item/vnd.cs4730.Category";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public boolean onCreate(){
        db = new Prog5Database(getContext());
        db.open();
        return true;
    }


    // -------Delete-------
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){

        boolean isTrans = false;
        switch (uriMatcher.match(uri)){
            case TRANS:
                isTrans = true;
                break;
            case TRANS_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                isTrans = true;
                break;
            case CAT:
                break;
            case CAT_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count;
        if(isTrans){
            count = db.cpDelete(prog5SQLiteHelper.TRANS_TABLE_NAME, selection, selectionArgs);
        } else {
            count = db.cpDelete(prog5SQLiteHelper.CAT_TABLE_NAME, selection, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    //-------Query-------
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        boolean isTrans = false;
        switch (uriMatcher.match(uri)){
            case TRANS:
                isTrans = true;
                break;
            case TRANS_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                isTrans = true;
                break;
            case CAT:
                break;
            case CAT_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor c;
        if(isTrans){
            c = db.cpQueryJoin(prog5SQLiteHelper.TRANS_TABLE_NAME, projection, selection, selectionArgs, sortOrder);
        } else {
            c = db.cpQuery(prog5SQLiteHelper.CAT_TABLE_NAME, projection, selection, selectionArgs, sortOrder);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    //-------Insert-------
    @Override
    public Uri insert(Uri uri, ContentValues values){
        boolean isTrans = uriMatcher.match(uri) == TRANS;
        boolean isCat = uriMatcher.match(uri) == CAT;
        if(!isTrans && !isCat){
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (values == null) {
            values = new ContentValues();
        }

        long rowId;
        Uri noteUri;
        if(isTrans){
            rowId = db.cpInsert(prog5SQLiteHelper.TRANS_TABLE_NAME, values);
            if(rowId > 0) {
                noteUri = ContentUris.withAppendedId(CONTENT_URI_trans, rowId);
                getContext().getContentResolver().notifyChange(noteUri, null);
                return noteUri;
            }
        }
        if(isCat){
            rowId = db.cpInsert(prog5SQLiteHelper.CAT_TABLE_NAME, values);
            if(rowId > 0) {
                noteUri = ContentUris.withAppendedId(CONTENT_URI_cat, rowId);
                getContext().getContentResolver().notifyChange(noteUri, null);
                return noteUri;
            }
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    //-------Update-------
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        boolean isTrans = false;
        switch (uriMatcher.match(uri)){
            case TRANS:
                isTrans = true;
                break;
            case TRANS_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                isTrans = true;
                break;
            case CAT:
                break;
            case CAT_ID:
                selection = selection + "_id = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count;
        if(isTrans){
            count = db.cpUpdate(prog5SQLiteHelper.TRANS_TABLE_NAME, values, selection, selectionArgs);
        } else {
            count = db.cpUpdate(prog5SQLiteHelper.CAT_TABLE_NAME, values, selection, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

