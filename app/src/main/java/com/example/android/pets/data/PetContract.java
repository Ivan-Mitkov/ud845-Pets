package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by ivan on 29.9.2017 г..
 */

public final class PetContract {
    private PetContract(){}
    /* Inner class that defines the table contents */
    public static class PetEntry implements BaseColumns{
        public static String _ID=BaseColumns._ID;
        public static final String TABLE_NAME = "pets";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_BREED = "breed";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        /**
         * Possible values for the gender.
         */
        public static final int UNKNOWN = 0;
        public static final int MALE = 1;
        public static final int FEMALE = 2;
    }
}
