package com.example.dilyara.coursovaja.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.harrix.sqliteexample.DatabaseHelper;

import java.io.IOException;

/**
 * Created by Dilyara on 10.12.2017.
 */

public class DataBaseMetods {
    static DatabaseHelper mDBHelper;
    static SQLiteDatabase mDb;

    static void Connect(Context context) {
        mDBHelper = new DatabaseHelper(context);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    static public Cursor SelectTasks(Context context) {
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Tasks WHERE Responsible = " + GlobalData.user.ID, null);
        cursor.moveToFirst();
        return cursor;
    }
    static public Cursor SelectTask(Context context, long id) {
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Tasks WHERE _id = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }
    static public Cursor SelectProjects(Context context)
    {
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Projects WHERE ProjectManeger = "+ GlobalData.user.ID, null);
        cursor.moveToFirst();
        return cursor;
    }
    static public Cursor SelectProject(Context context, long id) {
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Projects WHERE _id = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }
    static public Cursor SelectProjectTasks(Context context, long id) {
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Tasks WHERE Project = " + id, null);
        cursor.moveToFirst();
        return cursor;
    }
    static public void Delete(String table, String id){
        mDb.delete(table, "_id = ?", new String[]{String.valueOf(id)});
    }

}
