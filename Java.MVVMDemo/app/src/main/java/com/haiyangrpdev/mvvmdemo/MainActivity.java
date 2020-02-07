package com.haiyangrpdev.mvvmdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final Integer ADD_PERSON_REQUEST = 1;
    private PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PersonAdapter adapter = new PersonAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                startActivityForResult(intent, ADD_PERSON_REQUEST);
            }
        });

        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        personViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> persons) {
                // update UI
                adapter.setPersons(persons);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_PERSON_REQUEST && resultCode == RESULT_OK) {
            Person newPerson = (Person)data.getParcelableExtra(AddPersonActivity.EXTRA_PERSON);
            new AgentAsyncTask(this, newPerson).execute();

            Toast.makeText(this, "Person added.", Toast.LENGTH_LONG).show();
        }
    }

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> weakActivity;
        private Person newPerson;

        public AgentAsyncTask(Activity activity, Person person) {
            weakActivity = new WeakReference<>(activity);
            this.newPerson = person;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            PersonRepository personRepository = new PersonRepository(activity.getApplication());
            personRepository.insert(newPerson);
            return null;
        }
    }
}
