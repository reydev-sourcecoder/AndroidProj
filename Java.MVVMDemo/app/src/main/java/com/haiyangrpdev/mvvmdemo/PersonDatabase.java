package com.haiyangrpdev.mvvmdemo;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {

    private static PersonDatabase instance;
    public abstract PersonDao personDao();

    public static PersonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, "person_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private PersonDao personDao;

        public PopulateDBAsyncTask(PersonDatabase personDatabase) {
            personDao = personDatabase.personDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            personDao.insert(new Person("Rey", "rey@gmail.com",1));
            personDao.insert(new Person("Ken", "ken@gmail.com",2));
            personDao.insert(new Person("Joseph", "joseph@gmail.com",3));
            return null;
        }
    }
}
