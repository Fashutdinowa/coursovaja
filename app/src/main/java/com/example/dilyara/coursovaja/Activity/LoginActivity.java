package com.example.dilyara.coursovaja.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;
import com.example.dilyara.coursovaja.entity.Role;
import com.example.dilyara.coursovaja.entity.Status;
import com.example.dilyara.coursovaja.entity.User;
import com.example.dilyara.coursovaja.entity.Way;

import org.harrix.sqliteexample.DatabaseHelper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    EditText login;
    EditText password;
    boolean error;
    boolean errorpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataBaseMetods.Adddata(this);

    }

    public void onMyButtonClick(View view) {
        // выводим сообщение
        login = (EditText) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.password);
        Button sign_in = (Button) findViewById(R.id.autorization);
        mDBHelper = new DatabaseHelper(this);
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        Cursor cursor = mDb.rawQuery("SELECT * FROM Users WHERE Login Like '" + login.getText().toString() + "'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            GlobalData.user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            cursor.moveToNext();
        }
        if (GlobalData.user == null) {
            login.setTextColor(Color.RED);
            error = true;
            //добавить выделение ошибки красным цветом
            Toast.makeText(this, "Пользователь не найден. Проверьте правильность ввода логина и повторите попытку", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!GlobalData.user.Password.equals(password.getText().toString())) {
            errorpass = true;
            password.setTextColor(Color.RED);
           return;
        }
        cursor.close();
        mDb.close();
        mDBHelper.close();
        Intent intent = new Intent(LoginActivity.this, Tasks.class);
        startActivity(intent);
    }

    public void onClickPassw(View view) {
        try {
            if (errorpass)
                password.setText("");
            password.setTextColor(0xFF019EE4);
            errorpass = false;
        } catch (Exception e) {
        }
    }

    public void onClickLogin(View view) {
        try {
            if (error)
                login.setText("");
            login.setTextColor(0xFF019EE4);
            error = false;
        } catch (Exception e) {
        }
    }

    public  void onClickRegistration (View view){
        Intent intent = new Intent(LoginActivity.this, Registration.class);
        startActivity(intent);
    }


}
