package com.ikosmov.annnew2019;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


//import android.widget.Toolbar;
//активити создания и просмотра будильника
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    Button buttonBack1, AlarmOn, AlarmOff, buttonDel;
    RadioButton radioMon, radioTue, radioWed, radioThu, radioFri, radioSut, radioSun, radioPhis, radioLyrc, radioAll, radioSound, radioVibro, radioSoundAll;
    TextView TimeDisplay;
    TimePicker timePicker;
    GregorianCalendar calendar;
    PendingIntent pendingIntent;
    int id;
    int y = -1;
    int hour, minute, task, sound;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_copy);
        timePicker = (TimePicker) findViewById(R.id.TimePick);
        timePicker.setIs24HourView(true);
        AlarmOn = (Button) findViewById(R.id.AlarmOn);
        AlarmOff = (Button) findViewById(R.id.AlarmOff);
        calendar=new GregorianCalendar();

        radioPhis = (RadioButton) findViewById(R.id.radioPhis);
        radioLyrc = (RadioButton) findViewById(R.id.radioLyrc);
        radioAll = (RadioButton) findViewById(R.id.radioAll);
        radioSound = (RadioButton) findViewById(R.id.radioSound);
        radioVibro = (RadioButton) findViewById(R.id.radioVibro);
        radioSoundAll = (RadioButton) findViewById(R.id.radioSoundAll);
        final Context context = this;

        final TextView textView=findViewById(R.id.textView);
        // buttonBack1 = (Button) findViewById(R.id.buttonBack1);
        //buttonBack1.setOnClickListener(this);
        RadioGroup tg = findViewById(R.id.taskgroup);
        RadioGroup ts = findViewById(R.id.soundgroup);
        String ids = getIntent().getStringExtra("id");
//        проверка существования будильника-если существеут инициализируем интерфейс
        if (ids != null) {
            String years=getIntent().getStringExtra("year");
            String months=getIntent().getStringExtra("month");
            String days=getIntent().getStringExtra("day");
            String tasks = getIntent().getStringExtra("task");
            String rings = getIntent().getStringExtra("ring");
            String hours = getIntent().getStringExtra("hour");
            String minutes = getIntent().getStringExtra("minute");
            String num = getIntent().getStringExtra("number");

            int year=Integer.parseInt(years);
            int month=Integer.parseInt(months);
            int day=Integer.parseInt(days);
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            y = Integer.parseInt(num);
            minute = Integer.parseInt(minutes);
            id = Integer.parseInt(ids);
            hour = Integer.parseInt(hours);
            task = Integer.parseInt(tasks);
            sound = Integer.parseInt(rings);
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
            switch (task) {
                case 1:
                    tg.check(R.id.radioPhis);
                    break;
                case 2:
                    tg.check(R.id.radioLyrc);
                    break;
                case 3:
                    tg.check(R.id.radioAll);
                    break;
            }
            switch (sound) {
                case 1:
                    ts.check(R.id.radioSound);
                    break;
                case 2:
                    ts.check(R.id.radioVibro);
                    break;
                case 3:
                    ts.check(R.id.radioSoundAll);
                    break;
            }
        } else {id = -1;tg.check(R.id.radioAll);
        ts.check(R.id.radioSoundAll);
        }
        textView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR));

        final Calendar calendar = Calendar.getInstance();

        //
        final Intent my_intent = new Intent((Context) Main2Activity.this, AlarmRecevier.class);

        Context context1=this;
        //выбор даты будильника
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//      установка/изменение будильника
        final int id2=id;
        AlarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                if(id2!=-1){
                    Callback.Calls.main.turnOf(id2);
                }
                RadioGroup tg = findViewById(R.id.taskgroup);
                RadioGroup ts = findViewById(R.id.soundgroup);
                int task = 0;
                int sound = 0;
                int i = tg.getCheckedRadioButtonId();
                switch (i) {
                    case R.id.radioPhis:
                        task = 1;
                        break;
                    case R.id.radioLyrc:
                        task = 2;
                        break;
                    case R.id.radioAll:
                        task = 3;
                }
                i = ts.getCheckedRadioButtonId();
                switch (i) {
                    case R.id.radioSound:
                        sound = 1;
                        break;
                    case R.id.radioVibro:
                        sound = 2;
                        break;
                    case R.id.radioSoundAll:
                        sound = 3;
                        break;
                }
                if (id >= 0) {
                    int id1=Callback.Calls.main.listAlarmFragment.mas.get(id).id;
                    Callback.Calls.main.listAlarmFragment.mas.set(id, new AlarmInfoNow(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), task, sound));
                    //Callback.Calls.main.listAlarmFragment.mas.get(id).isOn = true;
                    Callback.Calls.main.listAlarmFragment.mas.get(id).id=id1;
                    ContentValues values=new ContentValues();
                    values.put("year",calendar.get(Calendar.YEAR));
                    values.put("month",calendar.get(Calendar.MONTH));
                    values.put("day",calendar.get(Calendar.DAY_OF_MONTH));
                    values.put("hour",timePicker.getHour());
                    values.put("minute",timePicker.getMinute());
                    values.put("ison",1);
                    values.put("task", task);
                    values.put("sound",sound);
                    Callback.Calls.database.update("alarms",values,"ID=?",new String[]{y+""});
                } else {
                    ContentValues values=new ContentValues();
                    values.put("year",calendar.get(Calendar.YEAR));
                    values.put("month",calendar.get(Calendar.MONTH));
                    values.put("day",calendar.get(Calendar.DAY_OF_MONTH));
                    values.put("hour",timePicker.getHour());
                    values.put("minute",timePicker.getMinute());
                    values.put("ison",1);int id=-1;
                    values.put("task", task);
                    values.put("sound",sound);
                    Callback.Calls.database.insert("alarms",null,values);
                    Cursor query = Callback.Calls.database.rawQuery("SELECT * FROM alarms ;", new String[]{});
                    if(query.moveToLast()){
                        id=query.getInt(0);
                    }
                    Callback.Calls.main.listAlarmFragment.mas.add( new AlarmInfoNow(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), task, sound));
                    Callback.Calls.main.listAlarmFragment.mas.get(Callback.Calls.main.listAlarmFragment.mas.size()-1).id=id;
                }
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
//                timePicker.setEnabled(false);
                //конвертируем в стринг
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                //косяки
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }
                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }
                setTimeText("Будильник поставлен на " + hour_string + ":" + minute_string);


                //
//                pendingIntent = pendingIntent.getBroadcast(Main2Activity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // задаем флфрмменеджеру то время, которое мы получили с помощью интента
                // отправляем в аларм ресейвер и когда срабатывает, выводит сообщение
                if (id == -1) {
                    id = Callback.Calls.main.listAlarmFragment.mas.size() - 1;
                    Callback.Calls.main.turnOn(id);
                    Callback.Calls.main.listAlarmFragment.update();
                } else {
                    Callback.Calls.main.turnOn(id);
                    Callback.Calls.main.listAlarmFragment.update();
                }

                finish();
            }

//
        });
        //отключить будильник если он существует
        //если нет то просто закрыть активити
        AlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setTimeText("Будильник выключен");

                if (id >= 0) {
                    ContentValues values=new ContentValues();
                    values.put("year",calendar.get(Calendar.YEAR));
                    values.put("month",calendar.get(Calendar.MONTH));
                    values.put("day",calendar.get(Calendar.DAY_OF_MONTH));
                    values.put("hour",timePicker.getHour());
                    values.put("minute",timePicker.getMinute());
                    values.put("ison",0);
                    values.put("task", task);
                    values.put("sound",sound);
                    Callback.Calls.database.update("alarms",values,"ID=?",new String[]{y+""});
                    Callback.Calls.main.turnOf(id);
//                    Callback.Calls.main.listAlarmFragment.mas.get(id).isOn = false;
                    Callback.Calls.main.listAlarmFragment.update();


                }
                finish();

            }
        });
    }

    private void setTimeText(String s) {
//        ((TextView) this.findViewById(R.id.updateTimeText)).setText(s);


    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.buttonBack1:
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//
//
//        }
    }

}
