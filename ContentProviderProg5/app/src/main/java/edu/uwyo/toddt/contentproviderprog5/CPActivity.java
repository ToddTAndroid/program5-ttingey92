package edu.uwyo.toddt.contentproviderprog5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.uwyo.toddt.contentproviderprog5.db.Prog5Database;
import edu.uwyo.toddt.contentproviderprog5.db.prog5SQLiteHelper;

public class CPActivity extends AppCompatActivity {

    Prog5Database db;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp);

        Button fill = (Button) findViewById(R.id.btn_fill);
        Button delete = (Button) findViewById(R.id.btn_delete);
        output = (TextView) findViewById(R.id.textDB_entries);

        db = new Prog5Database(super.getApplicationContext());
        db.open();

        fill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = "Databases populated!";
                output.setText(text);
                db.insertCat("Cheese");
                db.insertTrans("8/9/16", "Debit", "restaurant", "25.99", "Cheese");
                db.insertCat("Lump");
                db.insertTrans("9-25-1999", "Credit", "Mall", "36.99", "Lump");
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                db.cpDelete(prog5SQLiteHelper.TRANS_TABLE_NAME, null, null);
                db.cpDelete(prog5SQLiteHelper.CAT_TABLE_NAME, null, null);
                String text = "Databases deleted.";
                output.setText(text);
            }
        });
    }

//    //on resume, open the database again.
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (db == null)
//            db = new Prog5Database(this.getApplicationContext());
//        if (!db.isOpen())
//            db.open();
//    }
//
//    //onclose, we are in the background, so just close the DB.
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (db.isOpen())
//            db.close();
//
//    }

    public void appendthis(String item) {
        output.append(item + "\n");
    }
}
