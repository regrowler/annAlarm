package com.ikosmov.annnew2019;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {
    boolean f, s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Button button = findViewById(R.id.addTaskSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = ((EditText) findViewById(R.id.addTaskTask)).getText().toString();
                String s2 = ((EditText) findViewById(R.id.addTaskAnswer)).getText().toString();
                Callback.Calls.main.taskListFragment.adapter.tasks.add(new TaskInfoNow(s2, s1));
                int type=0;
                if(f)type=1;
                if(s)type=2;
//                Cursor q=Callback.Calls.database.rawQuery("INSERT INTO tasks(task,answer,type) VALUES(?,?,?)",new String[]{s1,s2,type+""});
//                q.close();
//                Callback.Calls.database.execSQL("INSERT INTO tasks(task,answer,type) VALUES('123','45',2);");
                ContentValues values=new ContentValues();
                values.put("task",s1);
                values.put("answer",s2);
                values.put("type",type);
                Callback.Calls.database.insert("tasks",null,values);
                Callback.Calls.main.taskListFragment.update();
                finish();
            }
        });
        f = s = false;
        final CheckBox fs = findViewById(R.id.checkBox2);
        final CheckBox sec = findViewById(R.id.checkBox);
        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!f && s) {
                    sec.setChecked(false);
                    s = !s;
                }
                f = !f;
            }
        });
        sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s && f) {
                    fs.setChecked(false);
                    f = !f;
                }
                s = !s;
            }

        });

    }

}

