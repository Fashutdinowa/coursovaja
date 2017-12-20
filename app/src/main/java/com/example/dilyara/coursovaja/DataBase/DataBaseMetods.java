package com.example.dilyara.coursovaja.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.system.StructStat;
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
    static public void Delete(Context context, String table, String id){
        Connect(context);
        mDb.delete(table, "_id = ?", new String[]{String.valueOf(id)});
        Toast.makeText(context, "Удаление успешно", Toast.LENGTH_SHORT).show();
        mDb.close();
        mDBHelper.close();

    }
    /////////////////////////////////////////////////////////////////////////////////
    static public void Adddata(Context context)
    {
        Connect(context);
        GlobalData.ways.clear();
        GlobalData.roles.clear();
        GlobalData.status.clear();
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
    ////////////////////////////////////////////////////////////////////////////////////////
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
    static public long AddDataTasks(Context context, String Name, long Status, String CrDate, String CompDate, long Project, String Descr, long Resp){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Name", Name);
        cv.put("Status", Status);
        cv.put("CreateDate", CrDate);
        cv.put("CompletionDate", CompDate);
        cv.put("Project", Project);
        cv.put("Description", Descr);
        cv.put("Responsible", Resp);
        long rezult = mDb.insert("Tasks",null, cv);
        if (rezult>0)
        {
            Toast.makeText(context, "Задача добавлена", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        mDb.close();
        mDBHelper.close();
        return rezult;

    }
    static public long UpdateDataTasks(Context context, long id, String Name, long Status, String CrDate, String CompDate, long Project, String Descr, long Resp){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Status", Status);
        cv.put("CreateDate", CrDate);
        cv.put("CompletionDate", CompDate);
        cv.put("Project", Project );
        cv.put("Description", Descr);
        cv.put("Responsible", Resp);
        long rezult = mDb.update("Tasks", cv, "_id = "+Long.toString(id), null);
        if (rezult>0)
        {
            Toast.makeText(context, "Задача обновлена", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        mDb.close();
        mDBHelper.close();
        return rezult;

    }

    /////////////////////////////////////////////////////////////////////
    static public Cursor SelectProjects(Context context){
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
    static public long AddDataProject(Context context, String Name, long Status,long Way, String CrDate, String CompDate, String Descr, long Resp){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Name", Name);
        cv.put("Status", Status);
        cv.put("Way", Way);
        cv.put("CreateDate", CrDate);
        cv.put("CompletionDate", CompDate);
        cv.put("Description", Descr);
        cv.put("ProjectManeger", Resp);
        long rezult = mDb.insert("Projects",null, cv);
        if (rezult>0)
        {
            Toast.makeText(context, "Проект добавлен", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        mDb.close();
        mDBHelper.close();
        return rezult;
    }

    static public long UpdateDataProject(Context context,long id, String Name, long Status,long Way, String CrDate, String CompDate, String Descr, long Resp){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Name", Name);
        cv.put("Status", Status);
        cv.put("Way", Way);
        cv.put("CreateDate", CrDate);
        cv.put("CompletionDate", CompDate);
        cv.put("Description", Descr);
        cv.put("ProjectManeger", Resp);
        long rezult = mDb.update("Projects", cv, "_id ="+Long.toString(id), null);
        if (rezult>0)
        {
            Toast.makeText(context, "Проект обновлен", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        mDb.close();
        mDBHelper.close();
        return rezult;
    }
    static public Cursor SelectAllProject(Context context)
    {
        int count =0;
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Projects", null);
        cursor.moveToFirst();
        return cursor;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    static public long AddDataUser(Context context, String Name, String Surname, long Role, String Login, String Password){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Name", Name);
        cv.put("Surname", Surname);
        cv.put("Role", Role+1);
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
        mDb.close();
        mDBHelper.close();
        return rezult;
    }
    static public long UpdateDataUser(Context context, long id, String  Name, String Surname, long Role){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Name", Name);
        cv.put("Surname", Surname);
        cv.put("Role", Role+1);
        long rezult = mDb.update("Users",cv, "_id ="+Long.toString(id), null);
        if (rezult>0)
        {
            Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        mDb.close();
        mDBHelper.close();
        return rezult;
    }
    static public long UpdatePassword(Context context, long id, String Password){
        Connect(context);
        ContentValues cv = new ContentValues();
        cv.put("Password", Password);
        long rezult = mDb.update("Users",cv, "_id ="+Long.toString(id), null);
        if (rezult>0)
        {
            Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
        mDb.close();
        mDBHelper.close();
        return rezult;
    }
    static public boolean SelectUser(Context context, String Login)
    {
        int count =0;
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Users WHERE Login Like '" + Login + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            count++;
            cursor.moveToNext();
        }
        if (count>0)
            return false;
        else
            return true;
    }
    static public Cursor SelectAllUser(Context context)
    {
        int count =0;
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Users", null);
        cursor.moveToFirst();
        return cursor;
    }
    static public String SelectUserID(Context context, String id)
    {
        String Fio = "";
        Connect(context);
        Cursor cursor = mDb.rawQuery("SELECT * FROM Users WHERE _id =" + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Fio+=cursor.getString(1)+" "+cursor.getString(2);
            cursor.moveToNext();
        }
        return Fio;
    }






}
