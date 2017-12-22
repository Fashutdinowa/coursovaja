package com.example.dilyara.coursovaja.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;

public class EditPassword extends AppCompatActivity {

    boolean error = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
    }
    public void PassOnClick(View view) {
        EditText old_pass = (EditText) findViewById(R.id.editText2);
        EditText new_pass = (EditText) findViewById(R.id.editText3);
        if (!old_pass.getText().toString().equals("")) {
            if (!new_pass.getText().toString().equals("")) {

                if (GlobalData.user.Password.equals(old_pass.getText().toString())) {
                    DataBaseMetods.UpdatePassword(this, GlobalData.user.ID, new_pass.getText().toString());
                } else {
                    error = true;
                    old_pass.setTextColor(Color.RED);
                    Toast.makeText(this, "Неправильный пароль", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Заполните поле  новый пароль", Toast.LENGTH_SHORT).show();
                new_pass.setHintTextColor(Color.RED);
            }
        }
        else {
            Toast.makeText(this, "Заполните поле  старый пароль", Toast.LENGTH_SHORT).show();
            old_pass.setHintTextColor(Color.RED);
            error = true;
        }
    }
    public void OldPassClick(View view){
        EditText old_pass = (EditText) findViewById(R.id.editText2);
        if (error)
        {
            error = false;
            old_pass.setTextColor(0xFF019EE4);
            old_pass.setHintTextColor(0xFF019EE4);
            old_pass.setText("");
        }
    }

    public void NewPassClick(View view){
        EditText old_pass = (EditText) findViewById(R.id.editText3);
        old_pass.setTextColor(0xFF019EE4);
        old_pass.setHintTextColor(0xFF019EE4);
    }


}
