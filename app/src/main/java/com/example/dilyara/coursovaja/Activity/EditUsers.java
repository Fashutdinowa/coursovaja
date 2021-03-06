package com.example.dilyara.coursovaja.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;
import com.example.dilyara.coursovaja.entity.Role;

public class EditUsers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);
        Spinner status = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<Role> adapter  =  new ArrayAdapter<Role>(
                this, android.R.layout.simple_spinner_item, GlobalData.roles);
        status.setAdapter(adapter);
        EditText Name = (EditText) findViewById(R.id.editText12);
        EditText SurName = (EditText) findViewById(R.id.editText11);
        Name.setText(GlobalData.user.Name);
        SurName.setText(GlobalData.user.Surname);
        status.setSelection((GlobalData.user.Role)-1);
    }
    public void OnEditClick(View view) {
        EditText Name = (EditText) findViewById(R.id.editText12);
        EditText SurName = (EditText) findViewById(R.id.editText11);
        Spinner status = (Spinner) findViewById(R.id.spinner);
        if (!SurName.getText().toString().equals("")) {
            if (!Name.getText().toString().equals("")) {
                try {
                    DataBaseMetods.UpdateDataUser(this, GlobalData.user.ID, Name.getText().toString(), SurName.getText().toString(), status.getSelectedItemPosition());
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Name.setHintTextColor(Color.RED);
                Toast.makeText(this, "Заполните поле имя", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            SurName.setHintTextColor(Color.RED);
            Toast.makeText(this, "Заполните поле фамилия", Toast.LENGTH_SHORT).show();
        }
    }

    public void SurnameOnClick(View view)
    {
        EditText SurName = (EditText) findViewById(R.id.editText11);
        SurName.setHintTextColor(0xFF019EE4);
    }
    public void NameOnClick(View view)
    {
        EditText SurName = (EditText) findViewById(R.id.editText12);
        SurName.setHintTextColor(0xFF019EE4);
    }

}
