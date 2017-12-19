package com.example.dilyara.coursovaja.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;
import com.example.dilyara.coursovaja.entity.Role;
import com.example.dilyara.coursovaja.entity.Status;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.regex.Pattern.matches;

public class EditTask extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    long t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Spinner status = (Spinner) findViewById(R.id.spinner4);
        Spinner project = (Spinner) findViewById(R.id.spinner5);
        Spinner users = (Spinner) findViewById(R.id.spinner6);

        ArrayAdapter<Status> adapter  =  new ArrayAdapter<Status>(
                this, android.R.layout.simple_spinner_item, GlobalData.status);
        status.setAdapter(adapter);

        try {
            Cursor cursor = DataBaseMetods.SelectAllUser(this);
            SimpleCursorAdapter userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
                    cursor, new String[]{"Surname", "Name"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            users.setAdapter(userAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            Cursor cursor = DataBaseMetods.SelectAllProject(this);
            SimpleCursorAdapter userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
                    cursor, new String[]{"Name"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            project.setAdapter(userAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        intent = getIntent();
        t = intent.getLongExtra("tata", -1);
        if (t>-1)
        {
            EditText name = (EditText)findViewById(R.id.editText6);
            EditText cteateDate = (EditText)findViewById(R.id.editText7);
            EditText comoletionDate = (EditText)findViewById(R.id.editText);
            AutoCompleteTextView deskription = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
            Cursor cursor = DataBaseMetods.SelectTask(this, t);
           while (!cursor.isAfterLast()) {
               name.setText(cursor.getString(1));
               status.setSelection(cursor.getInt(2)-1);
                cteateDate.setText(cursor.getString(3));
                comoletionDate.setText(cursor.getString(4));
                project.setSelection(cursor.getInt(5)-1);
                deskription.setText(cursor.getString(6));
               users.setSelection(cursor.getInt(7)-1);
                cursor.moveToNext();
            }
//            cursor.close();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    boolean error = false;
    boolean errorcomp = false;
    boolean errorcreate = false;
    public void onClickTask(View view)
    {
        try {
            EditText name = (EditText) findViewById(R.id.editText6);
            EditText cteateDate = (EditText) findViewById(R.id.editText7);
            EditText comoletionDate = (EditText) findViewById(R.id.editText);
            AutoCompleteTextView deskription = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
            Spinner status = (Spinner) findViewById(R.id.spinner4);
            Spinner project = (Spinner) findViewById(R.id.spinner5);
            Spinner users = (Spinner) findViewById(R.id.spinner6);
            if (matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d", cteateDate.getText().toString())) {
                if (matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d", comoletionDate.getText().toString())) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date create = format.parse(cteateDate.getText().toString());
                    Date comp = format.parse(comoletionDate.getText().toString());
                    if (create.before(comp)) {
                        if (t > -1) {
                            DataBaseMetods.UpdateDataTasks(this, t, name.getText().toString(), status.getSelectedItemId(), cteateDate.getText().toString(), comoletionDate.getText().toString(), project.getSelectedItemId(), deskription.getText().toString(), users.getSelectedItemId());
                        } else {
                            DataBaseMetods.AddDataTasks(this, name.getText().toString(), status.getSelectedItemId(), cteateDate.getText().toString(), comoletionDate.getText().toString(), project.getSelectedItemId(), deskription.getText().toString(), users.getSelectedItemId());
                        }
                    } else {
                        error = true;
                        Toast.makeText(this, "Дата старта проекта не может быть позже даты окончания", Toast.LENGTH_SHORT).show();
                        cteateDate.setTextColor(Color.RED);
                        comoletionDate.setTextColor(Color.RED);
                    }
                } else {
                    errorcreate = true;
                    Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show();
                    comoletionDate.setTextColor(Color.RED);
                }
            }
            else
            {
                errorcomp = true;
                Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show();
                cteateDate.setTextColor(Color.RED);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickCreate(View view)
    {
        EditText cteateDate = (EditText) findViewById(R.id.editText7);
        if (error)        {

            EditText comoletionDate = (EditText) findViewById(R.id.editText);
            cteateDate.setTextColor(0xFF019EE4);
            comoletionDate.setTextColor(0xFF019EE4);
        }
        if (errorcreate)
        {
            cteateDate.setTextColor(0xFF019EE4);
        }
    }
    public void onClickCompl(View view)
    {
        EditText comoletionDate = (EditText) findViewById(R.id.editText);

        if (error) {

            EditText cteateDate = (EditText) findViewById(R.id.editText7);
            cteateDate.setTextColor(0xFF019EE4);
            comoletionDate.setTextColor(0xFF019EE4);
        }
        if (errorcreate)
        {
            comoletionDate.setTextColor(0xFF019EE4);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, Projects.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, Tasks.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
