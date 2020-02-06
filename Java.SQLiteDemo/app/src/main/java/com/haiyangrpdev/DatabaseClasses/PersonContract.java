package com.haiyangrpdev.DatabaseClasses;

import android.provider.BaseColumns;

public final class PersonContract {

    private PersonContract() { }

    public static class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "TBL_PERSON";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUM_NAME_PHONE_NUMBER = "Phone Number";
    }

    public static class PersonQueries {
        public static final String SQL_CREATE_PERSON = "CREATE TABLE " + PersonEntry.TABLE_NAME + "(" +
                PersonEntry._ID + " INTEGER PRIMARY KEY," +
                PersonEntry.COLUMN_NAME_NAME + ", " +
                PersonEntry.COLUM_NAME_PHONE_NUMBER + ")";

        public static final String SQL_DELETE_PERSONS = "DROP TABLE IF EXISTS " + PersonEntry.TABLE_NAME;
    }
}
