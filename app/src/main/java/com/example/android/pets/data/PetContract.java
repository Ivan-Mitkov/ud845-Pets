package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ivan on 29.9.2017 г..
 */

public final class PetContract {
    //Uri constants
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "pets";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

    private PetContract(){}
    /* Inner class that defines the table contents */
    public static class PetEntry implements BaseColumns{
        //columns names
        public static final String _ID=BaseColumns._ID;
        public static final String TABLE_NAME = "pets";
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        //gender options
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;



    }
}
