package com.sandeep.forumappstask.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sandeep on 17-06-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "My_Database";
    public static final String TABLE_NAME = "messages";
    public static final String ID = "id";
    //public static final String name = "name";
    public static final String TYPE = "type";
    public static final String EMAIL = "email";
    public static final String SENDER_ID = "senderid";
    public static final String RECEIVER_ID = "receiverid";
    public static final String MESSAGE = "message";
    public static final String TIME_STAMP = "time";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_USERS = "users";
    public static final String USER_EMAIL = "email";
    public static final String USER_UNIQUE_ID = "unique_id";
    public static final String USER_TOKEN = "usertoken";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE + " TEXT,"
                + EMAIL + " TEXT,"
                + SENDER_ID +" TEXT, "
                + RECEIVER_ID + " TEXT,"
                + MESSAGE + " TEXT,"
                + TIME_STAMP + " TEXT"+ ")";
        db.execSQL(query);


        String users = "CREATE TABLE " + TABLE_USERS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_EMAIL + " TEXT,"
                + USER_UNIQUE_ID + " TEXT,"
                + USER_TOKEN + " TEXT"+ ")";
        db.execSQL(users);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
