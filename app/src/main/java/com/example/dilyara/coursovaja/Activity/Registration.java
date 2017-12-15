package com.example.dilyara.coursovaja.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.dilyara.coursovaja.R;

public class Registration extends AppCompatActivity {
    final Spinner spinner = (Spinner)findViewById(R.id.faculty);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, Projects.class);
                startActivity(intent);
            }
        });
    }

    public void RegButtonClick(View view) {


    }

}

