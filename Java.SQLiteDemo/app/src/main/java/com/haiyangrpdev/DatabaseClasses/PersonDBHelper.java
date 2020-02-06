package com.haiyangrpdev.DatabaseClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.haiyangrpdev.DatabaseClasses.PersonContract.*;

public class PersonDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Person.db";
    private static int DV_VERSION = 1;

    private Context mContext;

    public PersonDBHelper(Context context) {
        super(context, DB_NAME, null, DV_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(PersonQueries.SQL_CREATE_PERSON);
        }
        catch (Exception ex) {
            Toast.makeText(mContext,"Error on creating Person table.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(PersonQueries.SQL_DELETE_PERSONS);
        onCreate(db);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
