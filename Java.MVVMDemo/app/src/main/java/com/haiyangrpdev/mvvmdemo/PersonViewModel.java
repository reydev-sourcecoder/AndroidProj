package com.haiyangrpdev.mvvmdemo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository personRepository;
    private LiveData<List<Person>> allPersons;

    public PersonViewModel(@NonNull Application application) {
        super(application);

        personRepository = new PersonRepository(application);
        allPersons = personRepository.getAll();
    }

    public LiveData<List<Person>> getAllPersons() {
        return allPersons;
    }

    public void addPersonToDB(Person person) {
        personRepository.insert(person);
    }

    public void deletePersonFromDB(Person person) {
        personRepository.delete(person);
    }
}
