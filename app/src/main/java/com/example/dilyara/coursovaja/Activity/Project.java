package com.example.dilyara.coursovaja.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.dilyara.coursovaja.DataBase.DataBaseMetods;
import com.example.dilyara.coursovaja.DataBase.GlobalData;
import com.example.dilyara.coursovaja.R;

public class Project extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        TextView name = (TextView) findViewById(R.id.textView);
        TextView date = (TextView) findViewById(R.id.textView6);
        TextView way = (TextView) findViewById(R.id.textView17);
        TextView stat = (TextView) findViewById(R.id.textView14);
        TextView otw = (TextView) findViewById(R.id.textView18);
        TextView descr = (TextView) findViewById(R.id.textView11);
        ListView tasks = (ListView) findViewById(R.id.proj_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        intent = getIntent();
        id = intent.getLongExtra("tata", 0);
        Cursor cursor = DataBaseMetods.SelectProject(this, id);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name.setText(cursor.getString(1));
            stat.setText(GlobalData.status.get(cursor.getInt(2)-1).Name);
            way.setText(GlobalData.ways.get(cursor.getInt(3)-1).Name);
            date.setText(cursor.getString(4) + "-" + cursor.getString(5));
            descr.setText(cursor.getString(6));
            otw.setText(DataBaseMetods.SelectUserID(this, Long.toString(cursor.getLong(7))));
            cursor.moveToNext();
        }
        cursor.close();
        cursor = DataBaseMetods.SelectProjectTasks(this, id);
        SimpleCursorAdapter userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursor, new String []{"Name", "Status"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        tasks.setAdapter(userAdapter);

        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Intent intent = new Intent(Project.this, Task.class);
                intent.putExtra("tata", id);
                startActivity(intent);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

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
        getMenuInflater().inflate(R.menu.project, menu);
        TextView name = (TextView)findViewById(R.id.textView9);
        TextView role = (TextView)findViewById(R.id.textView10);
        name.setText(GlobalData.user.Surname+" "+ GlobalData.user.Name);
        role.setText(GlobalData.roles.get(GlobalData.user.Role-1).Name);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id_ = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id_ == R.id.action_settings) {
            Intent intent = new Intent(this, EditProject.class);
            intent.putExtra("tata", id);
            startActivity(intent);
            return true;
        }
        if (id_ == R.id.action_settings1) {
            ListView tasks = (ListView) findViewById(R.id.proj_task);
            if (tasks.getCount()<1) {
                DataBaseMetods.Delete(this, "Projects", Long.toString(id));
                return true;
            }
            else{
                AlertDialog.Builder ad;
                Context context = Project.this;
                String title = "Удаление проекта";
                String message = "У данного проекта есть прикреленные задачи. Удалить проект вместе с задачами?";
                String button1String = "Да";
                String button2String = "Нет";

                ad = new AlertDialog.Builder(context);
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        DataBaseMetods.DeleteTasksProj(Project.this, Long.toString(id));
                        DataBaseMetods.Delete(Project.this, "Projects", Long.toString(id));
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        return;
                    }
                });
                ad.show();
            }
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


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
