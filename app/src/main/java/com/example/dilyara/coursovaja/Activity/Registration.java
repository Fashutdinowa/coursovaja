package com.example.dilyara.coursovaja.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;
import com.example.dilyara.coursovaja.entity.Role;

public class Registration extends AppCompatActivity {
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
        EditText name = (EditText) findViewById(R.id.last_name);
        EditText surname = (EditText) findViewById(R.id.name);
        Spinner status = (Spinner) findViewById(R.id.faculty);
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password);

        try {

            if (DataBaseMetods.AddDataUser(this, name.getText().toString(), surname.getText().toString(), status.getSelectedItemPosition(), login.getText().toString(), password.getText().toString()) > 0) {
                Intent intent = new Intent(Registration.this, LoginActivity.class);
                startActivity(intent);
            }
            else {
                return;

            }
        }
        catch (Exception e) {
            return;
        }
    }

}

