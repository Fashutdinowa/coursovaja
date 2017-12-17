package com.example.dilyara.coursovaja.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.example.dilyara.coursovaja.entity.Role;
import com.example.dilyara.coursovaja.entity.Status;
import com.example.dilyara.coursovaja.entity.Way;

import org.harrix.sqliteexample.DatabaseHelper;

import java.io.IOException;

/**
 * Created by Dilyara on 10.12.2017.
 */

public class DataBaseMetods {
    static DatabaseHelper mDBHelper;
    static SQLiteDatabase mDb;

    static public void Connect(Context context) {
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
    static public void Adddata(Context context)
    {
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Ways", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GlobalData.ways.add(new Way(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        cursor = mDb.rawQuery("SELECT * FROM Roles", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GlobalData.roles.add(new Role(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        cursor = mDb.rawQuery("SELECT * FROM Status", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GlobalData.status.add(new Status(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
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
    static public void Delete(Context context, String table, String id){
        Connect(context);
        mDb.delete(table, "_id = ?", new String[]{String.valueOf(id)});
        Toast.makeText(context, "Удаление успешно", Toast.LENGTH_SHORT).show();

    }
    static public long AddDataUser(Context context, String Name, String Surname, long Role, String Email, String Login, String Password){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Name", Name);
        cv.put("Surname", Surname);
        cv.put("Role", Role);
        cv.put("Email", Email);
        cv.put("Login", Login);
        cv.put("Password", Password);
        long rezult = mDb.insert("Users",null, cv);
        if (rezult>0)
        {
            Toast.makeText(context, "Пользователь успешно добавлен", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        return rezult;

    }

}
