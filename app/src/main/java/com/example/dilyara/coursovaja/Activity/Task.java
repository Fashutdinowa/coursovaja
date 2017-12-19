package com.example.dilyara.coursovaja.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;
import com.example.dilyara.coursovaja.entity.Way;

import org.harrix.sqliteexample.DatabaseHelper;

public class Task extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    long id_proj;
    long t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        TextView name = (TextView) findViewById(R.id.textView5);
        TextView stat = (TextView) findViewById(R.id.textView8);
        TextView date = (TextView) findViewById(R.id.textView13);
        TextView project = (TextView) findViewById(R.id.textView15);
        TextView deskript = (TextView) findViewById(R.id.textView16);
        TextView otvewetst = (TextView) findViewById(R.id.textView19);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        t = intent.getLongExtra("tata", 0);
        Cursor cursor = DataBaseMetods.SelectTask(this, t);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name.setText(cursor.getString(1));
            stat.setText(GlobalData.status.get(cursor.getInt(2)-1).Name);
            date.setText(cursor.getString(3) + " - " + cursor.getString(4));
            id_proj = cursor.getInt(5);
            deskript.setText(cursor.getString(6));
            otvewetst.setText(GlobalData.user.Name+" "+GlobalData.user.Surname);
            cursor.moveToNext();
        }
        cursor.close();
        cursor = DataBaseMetods.SelectProject(this, id_proj);
        cursor.moveToFirst();
        project.setText(cursor.getString(1));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cursor.close();
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
        getMenuInflater().inflate(R.menu.task, menu);
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
            Intent intent = new Intent(this, EditTask.class);
            intent.putExtra("tata", t);
            startActivity(intent);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_settings1) {
            if (GlobalData.user.Role>1) {
                DataBaseMetods.Delete(this, "Tasks", Long.toString(t));
                this.finish();
            }
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
            Intent intent = new Intent(this, Projects.class);
            startActivity(intent);
            // Handle the camera action
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
    public void onClickProject(View view) {
        if (GlobalData.user.Role>1)
        {
            Intent intent = new Intent(this, Project.class);
            intent.putExtra("tata", id_proj);
            startActivity(intent);
        }
    }
}
