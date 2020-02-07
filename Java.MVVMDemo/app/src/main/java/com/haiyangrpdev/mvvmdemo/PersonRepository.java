package com.haiyangrpdev.mvvmdemo;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class PersonRepository {

    private PersonDao personDao;
    private LiveData<List<Person>> allPersons;

    public PersonRepository(Application application) {
        PersonDatabase personDB = PersonDatabase.getInstance(application);
        personDao = personDB.personDao();
        allPersons = personDao.getAll();
    }

    public void insert(Person person) {
        new InsertPersonAsyncTask(personDao).doInBackground(person);
    }

    public void update(Person person) {
        new UpdatePersonAsyncTask(personDao).doInBackground(person);
    }

    public void delete(Person person) {
        new DeletePersonAsyncTask(personDao).doInBackground(person);
    }

    public LiveData<List<Person>> getAll() {
        return allPersons;
    }

    public static class InsertPersonAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao personDao;

        public InsertPersonAsyncTask(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.insert(people[0]);
            return null;
        }
    }

    public static class UpdatePersonAsyncTask extends  AsyncTask<Person, Void, Void> {
        private PersonDao personDao;

        public UpdatePersonAsyncTask(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.update(people[0]);
            return null;
        }
    }

    public static class DeletePersonAsyncTask extends  AsyncTask<Person, Void, Void> {
        private PersonDao personDao;

        public DeletePersonAsyncTask(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.delete(people[0]);
            return null;
        }
    }
}
