package com.haiyangrpdev.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haiyangrpdev.myapplication.DB.Person;
import com.haiyangrpdev.myapplication.DB.PersonContract;
import com.haiyangrpdev.myapplication.DB.PersonDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PersonDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new PersonDBHelper(this);
        setContentView(R.layout.activity_main);
    }

    public void SavePerson(View view) {
        EditText etName = findViewById(R.id.etName);
        EditText etPhoneNo = findViewById(R.id.etPhoneNumber);

        String name = etName.getText().toString();
        String phoneNoStr = etPhoneNo.getText().toString();

        if (!(name.isEmpty() && phoneNoStr.isEmpty()) ) {
            int phoneNo = Integer.parseInt(phoneNoStr);
            Person person = new Person(name,phoneNo);
            insertToDB(person);
        }
    }

    private void insertToDB(Person person) {
        try {
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(PersonContract.PersonEntry.COLUMN_NAME_NAME, person.getName());
            values.put(PersonContract.PersonEntry.COLUMN_NAME_PHONE_NUMBER, person.getMobileNumber());

            // Insert the new row, returning the primary key value of the new one
            int newRowId = (int)db.insert(PersonContract.PersonEntry.TABLE_NAME,null, values);

            if(newRowId < 0) {
                Toast.makeText(this,
                        "There was an error in inserting data.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private List<Person> getPersons () {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Person> persons = new ArrayList<Person>();

        String[] projection = {
                BaseColumns._ID,
                PersonContract.PersonEntry.COLUMN_NAME_NAME,
                PersonContract.PersonEntry.COLUMN_NAME_PHONE_NUMBER
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = PersonContract.PersonEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                PersonContract.PersonEntry.TABLE_NAME,  // The table to query
                projection,                             // The array of columns to return (pass null to get all)
                null,                              // The columns for the WHERE clause
                null,                          // The values for the WHERE clause
                null,                         // don't group the rows
                null,                          // don't filter by row groups
                sortOrder                              // The sort order
        );

        // Reading and getting the specific person
        while(cursor.moveToNext()) {
            String personId = cursor.getString(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry._ID));
            String personName = cursor.getString(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry.COLUMN_NAME_NAME));
            int personPhoneNo = cursor.getInt(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry.COLUMN_NAME_PHONE_NUMBER));

            Person person = new Person(personName, personPhoneNo);
            person.setId(personId);
            persons.add(person);
        }

        return persons;
    }

    private Person getPersonById(int myId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Person personToReturn = null;

        String query = "SELECT * FROM TBL_PERSON WHERE _id = " + myId;
        Cursor cursor = db.rawQuery(query,null);

        // Reading and getting the specific person
        while(cursor.moveToNext()) {
            String personId = cursor.getString(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry._ID));
            if(personId.equals(String.valueOf(myId))) {
                String personName = cursor.getString(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry.COLUMN_NAME_NAME));
                int personPhoneNo = cursor.getInt(cursor.getColumnIndexOrThrow(PersonContract.PersonEntry.COLUMN_NAME_PHONE_NUMBER));
                personToReturn = new Person(personName, personPhoneNo);
            }
        }

        cursor.close();
        return personToReturn;
    }

    private void deletePersonById(String personId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = PersonContract.PersonEntry._ID + " =?";
        String[] selectionArgs = { personId };

        int deletedRows = db.delete(PersonContract.PersonEntry.TABLE_NAME, selection, selectionArgs);

        Toast.makeText(this,
                "Deleted " + deletedRows +" rows.", Toast.LENGTH_SHORT).show();
    }

    private void updatePerson(Person person) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(PersonContract.PersonEntry.COLUMN_NAME_NAME, person.getName());
        values.put(PersonContract.PersonEntry.COLUMN_NAME_PHONE_NUMBER, person.getMobileNumber());

        String selection = PersonContract.PersonEntry._ID + "=?";
        String[] selectionArgs = { person.getId() };

        int count = db.update(
                PersonContract.PersonEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Toast.makeText(this,
                "Updated " + count +" row.", Toast.LENGTH_SHORT).show();
    }
}
