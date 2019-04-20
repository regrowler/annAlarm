package com.ikosmov.annnew2019;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
//главное активити
@SuppressWarnings("ALL")
public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public ListAlarmFragment listAlarmFragment;
    public ListTasksFragment taskListFragment;
    Intent my_intent ;
    int c=0;

    final Calendar calendar = Calendar.getInstance();
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        //база данных открывается
        Callback.Calls.database=openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Callback.Calls.main=this;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listAlarmFragment=new ListAlarmFragment();
        taskListFragment=new ListTasksFragment();

        ShowFragment(1);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    //включить будильник по номерув массиве
    public void turnOn(int i){
        PendingIntent pendingIntent;
        AlarmInfoNow infoNow=listAlarmFragment.adapter.alarms.get(i);
        my_intent= new Intent((Context) Drawer.this, AlarmRecevier.class);
        my_intent.putExtra("tasktype",infoNow.typetask+"");
        my_intent.putExtra("id",infoNow.id+"");
        my_intent.putExtra("typesound",infoNow.typering+"");
        if(infoNow.isOn==true){
            turnOf(i);
        }
        calendar.set(Calendar.YEAR,infoNow.year);
        calendar.set(Calendar.MONTH,infoNow.month);
        calendar.set(Calendar.DAY_OF_MONTH,infoNow.day);
        calendar.set(Calendar.HOUR_OF_DAY, infoNow.hour);
        calendar.set(Calendar.MINUTE, infoNow.minute);
        int code=i*100+c;
        pendingIntent = PendingIntent.getBroadcast(Drawer.this,code , my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Callback.Calls.main.listAlarmFragment.mas.get(i).isOn = true;
    }
    //переключить состояние будильника по номеру в массиве
    public void switchT(int t){
        int y=0;
        AlarmInfoNow infoNow=listAlarmFragment.adapter.alarms.get(t);
        my_intent= new Intent((Context) Drawer.this, AlarmRecevier.class);
        my_intent.putExtra("tasktype",infoNow.typetask+"");
        my_intent.putExtra("typesound",infoNow.typering+"");
        my_intent.putExtra("id",infoNow.id);
        y=infoNow.id;
        ContentValues values=new ContentValues();
        values.put("year",infoNow.year);
        values.put("month",infoNow.month);
        values.put("day",infoNow.day);
        values.put("hour",infoNow.hour);
        values.put("minute",infoNow.minute);
        values.put("task", infoNow.typetask);
        values.put("sound",infoNow.typering);
        if(infoNow.isOn){
            Callback.Calls.main.turnOf(t);
            values.put("ison",0);
        }else {Callback.Calls.main.turnOn(t);
            values.put("ison",1);
        }
        Callback.Calls.database.update("alarms",values,"ID=?",new String[]{y+""});
        Callback.Calls.main.listAlarmFragment.mas.get(t).isOn=!Callback.Calls.main.listAlarmFragment.mas.get(t).isOn;
    }
    //выключить будильник по номеру в массиве
    public void turnOf(int i){
        PendingIntent pendingIntent;
        AlarmInfoNow infoNow=listAlarmFragment.adapter.alarms.get(i);
        if(infoNow.isOn){
            my_intent= new Intent((Context) Drawer.this, AlarmRecevier.class);
            my_intent.putExtra("tasktype",infoNow.typetask+"");
            my_intent.putExtra("typesound",infoNow.typering+"");
            my_intent.putExtra("id",infoNow.id);
            calendar.set(Calendar.YEAR,infoNow.year);
            calendar.set(Calendar.MONTH,infoNow.month);
            calendar.set(Calendar.DAY_OF_MONTH,infoNow.day);
            calendar.set(Calendar.HOUR_OF_DAY, infoNow.hour);
            calendar.set(Calendar.MINUTE, infoNow.minute);
            int code=i*100+c++;
            pendingIntent = PendingIntent.getBroadcast(Drawer.this, code, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
            }
            Callback.Calls.main.listAlarmFragment.mas.get(i).isOn = false;
        }
    }
    public void update(){
        int y=0;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            ShowFragment(1);
        } else if (id == R.id.nav_slideshow) {
            ShowFragment(2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //переключить экран(сменить фрагмент)
    public void ShowFragment(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case 1:
                fragment = listAlarmFragment;
                break;
            case 2:
                fragment = taskListFragment;
                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainframe, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
