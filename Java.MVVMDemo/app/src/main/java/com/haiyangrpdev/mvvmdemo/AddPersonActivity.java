package com.haiyangrpdev.mvvmdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddPersonActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "EXTRA_PERSON";

    private EditText etName;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        etName = findViewById(R.id.etName);
        etEmail= findViewById(R.id.etEmail);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Person");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                savePerson(new Person(etName.getText().toString(), etEmail.getText().toString(), 0));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void savePerson(Person person) {
        Intent data = new Intent();
        data.putExtra("EXTRA_PERSON", person);
        setResult(RESULT_OK, data);
        finish();
    }
}
