package com.haiyangrpdev.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        personViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                // update recycler view
            }
        });
    }
}
