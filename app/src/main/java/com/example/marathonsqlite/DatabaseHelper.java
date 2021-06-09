package com.example.marathonsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "candidates";

    // Table columns
    public static final String ID = "_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";


    private static final String DB_NAME = "marathon.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID
            + " LONG PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT NOT NULL, " + LAST_NAME + " TEXT NOT NULL, "
        + EMAIL + " TEXT NOT NULL UNIQUE, " + PHONE + " TEXT NOT NULL)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public  String addOne(Candidate candidate) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, candidate.getFirstName());
        contentValues.put(LAST_NAME, candidate.getLastName());
        contentValues.put(EMAIL, candidate.getEmail());
        contentValues.put(PHONE, candidate.getPhone());
        long insert = database.insert(TABLE_NAME, null, contentValues);
        if (insert == -1) {
            return  "has been added";
        }else {
            return "hasn't been added";
        }
    }

    public List<Candidate> getAll() {
        List<Candidate> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                Long phone = cursor.getLong(4);

                Candidate candidate = new Candidate(id,firstName, lastName, email, phone);
                list.add(candidate);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public boolean delete(Candidate candidate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = " + candidate.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return  true;
        } else {
            return false;
        }
    }

    public  int update (Long id, Candidate candidate) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, candidate.getFirstName());
        contentValues.put(LAST_NAME, candidate.getLastName());
        contentValues.put(EMAIL, candidate.getEmail());
        contentValues.put(PHONE, candidate.getPhone());
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + id, null);
        return i;
    }
}
