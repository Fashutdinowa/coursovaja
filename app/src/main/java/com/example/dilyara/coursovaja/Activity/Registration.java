package com.example.dilyara.coursovaja.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;
import com.example.dilyara.coursovaja.entity.Role;

public class Registration extends AppCompatActivity {

    boolean error = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Spinner status = (Spinner) findViewById(R.id.faculty);

        ArrayAdapter<Role> adapter  =  new ArrayAdapter<Role>(
                this, android.R.layout.simple_spinner_item, GlobalData.roles);
        status.setAdapter(adapter);
    }

    public void RegButtonClick(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        EditText surname = (EditText) findViewById(R.id.last_name);
        Spinner status = (Spinner) findViewById(R.id.faculty);
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password);
        if (!surname.getText().toString().equals("")) {
            if (!name.getText().toString().equals("")) {
                if (!login.getText().toString().equals("")) {
                    if (!password.getText().toString().equals("")) {
                        try {
                            if (DataBaseMetods.SelectUser(this, login.getText().toString())) {
                                if (DataBaseMetods.AddDataUser(this, surname.getText().toString(),name.getText().toString(), status.getSelectedItemPosition(), login.getText().toString(), password.getText().toString()) > 0) {
                                    Intent intent = new Intent(Registration.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    return;

                                }
                            } else {
                                login.setTextColor(Color.RED);
                                error = true;
                                Toast.makeText(this, "Пользователь с данным логином существует", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            return;
                        }
                    } else {
                        Toast.makeText(this, "Заполните поле пароль", Toast.LENGTH_SHORT).show();
                        password.setHintTextColor(Color.RED);
                    }
                } else {
                    Toast.makeText(this, "Заполните поле логин", Toast.LENGTH_SHORT).show();
                    login.setHintTextColor(Color.RED);
                    error = true;
                }
            } else {
                Toast.makeText(this, "Заполните поле имя", Toast.LENGTH_SHORT).show();
                name.setHintTextColor(Color.RED);
            }
        } else {
            Toast.makeText(this, "Заполните поле фамилия", Toast.LENGTH_SHORT).show();
            surname.setHintTextColor(Color.RED);
        }
    }

    public void LoginOnClick(View view){
        EditText login = (EditText) findViewById(R.id.login);
        if (error){
            error = false;
            login.setTextColor(0xFF019EE4);
            login.setText("");
        }
    }
    public void PasswordOnClick(View view){
        EditText login = (EditText) findViewById(R.id.password);
        login.setTextColor(0xFF019EE4);
        login.setHintTextColor(0xFF019EE4);
    }
    public void NameOnClick(View view){
        EditText login = (EditText) findViewById(R.id.name);
        login.setHintTextColor(0xFF019EE4);
        login.setTextColor(0xFF019EE4);
    }
    public void SurnameOnClick(View view) {
        EditText login = (EditText) findViewById(R.id.last_name);
        login.setHintTextColor(0xFF019EE4);

    }

}

