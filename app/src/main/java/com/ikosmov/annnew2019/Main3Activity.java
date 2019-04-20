package com.ikosmov.annnew2019;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//активит отвечающее зза будильник
public class Main3Activity extends AppCompatActivity {
    Button buttonTry;
    EditText editText;
    TextView textView;
    Vibrator vibrator;
    //инициализация будильнка
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.pidr);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textView=findViewById(R.id.textTast);
        Intent intent = getIntent();
        final List<TaskInfoNow> mas = new ArrayList<>();
        String tasktype = getIntent().getStringExtra("tasktype");
        String sound = getIntent().getStringExtra("typesound");
        SQLiteDatabase database = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String ids = getIntent().getStringExtra("id");
        int id1 = Integer.parseInt(ids);
        //отключение будильника в базе
        if (id1 >= 0) {
            ContentValues values = new ContentValues();
            values.put("ison", 0);
            database.update("alarms", values, "ID=?", new String[]{id1 + ""});
        }

        int task = Integer.parseInt(tasktype);
        int soundt = Integer.parseInt(sound);
        //выбор сигнала в соответствие свойствам
        if(soundt==1){
            mp.start();
        }else if(soundt==2){
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(1000000);
            }
        }else {
            mp.start();
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(1000000);
            }
        }
        Cursor query;
        //выбор заданий в соответствие со свойствами
        if (task == 1) {
            query = database.rawQuery("SELECT * FROM tasks WHERE type =?;", new String[]{"1"});
        } else if (task == 2) {
            query = database.rawQuery("SELECT * FROM tasks WHERE type =?;", new String[]{"2"});
        }else {
            query = database.rawQuery("SELECT * FROM tasks WHERE type in(?,?,?);", new String[]{"1","2","0"});
        }

        if (query.moveToFirst()) {
            do {
                String tasks = query.getString(1);
                String anwer = query.getString(2);
                int type = query.getInt(3);
                int id = query.getInt(0);
                mas.add(new TaskInfoNow(anwer, tasks));
                mas.get(mas.size() - 1).type = type;
                mas.get(mas.size() - 1).id = id;

            }
            while (query.moveToNext());
        }

        query.close();
        final Random random=new Random();
        int y=random.nextInt(mas.size());
        textView.setText(mas.get(y).task);
        editText = findViewById(R.id.editText);
        buttonTry = (Button) findViewById(R.id.buttonTry);
        final String an=mas.get(y).answer;
        //кнопка принимающая ответ
        buttonTry.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().toString().equals(an)) {
                            mp.stop();
                            vibrator.cancel();
                            startActivity(new Intent(Main3Activity.this, Drawer.class));
                            finish();
                        }
                    }
                }
        );
        Button ch=findViewById(R.id.buttonCh);
        //новая рандомная задача
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Random random=new Random();
                int y=random.nextInt(mas.size());
                textView.setText(mas.get(y).task);
                final String an=mas.get(y).answer;
                buttonTry.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (editText.getText().toString().equals(an)) {
                                    mp.stop();
                                    vibrator.cancel();
                                    startActivity(new Intent(Main3Activity.this, Drawer.class));
                                    finish();
                                }
                            }
                        }
                );
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
