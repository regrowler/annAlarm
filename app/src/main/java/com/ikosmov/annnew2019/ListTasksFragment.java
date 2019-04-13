package com.ikosmov.annnew2019;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ListTasksFragment extends Fragment {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    List<TaskInfoNow> mas;
    private Task2 mTask2;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_list_fragment, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Список задач");
        recyclerView=getActivity().findViewById(R.id.mainlist);
        mas=new ArrayList<>();
        adapter=new TaskAdapter(getContext(),mas);
//        mas.add(new TaskInfoNow("fra","asfqre"));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),AddTaskActivity.class);
                getActivity().startActivity(i);
            }
        });
        update();
    }
    public void update(){
        mTask2=new Task2();
        mTask2.execute((Void)null);
    }
    public class Task2 extends AsyncTask<Void, Void, Boolean> {
        private final ArrayList<TaskInfoNow> mas;
        public Task2(){mas=new ArrayList<>();}

        @Override
        protected Boolean doInBackground(Void... voids) {
            Callback.Calls.database.execSQL("CREATE TABLE IF NOT EXISTS tasks (" +
                    "ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
                    "task TEXT," +
                    "answer TEXT," +
                    "type INTEGER);");
//            Callback.Calls.database.execSQL("INSERT INTO user(name,age) VALUES ('pidr', 23);");
//            Callback.Calls.database.execSQL("INSERT INTO user(name,age) VALUES ('too', 31);");
//            SQLiteStatement s=db.compileStatement("SELECT * FROM user where id=?;");
//            Cursor query=s.
            Cursor query = Callback.Calls.database.rawQuery("SELECT * FROM tasks;", new String[]{});
            if (query.moveToFirst()) {
                do {
                    String task=query.getString(1);
                    String anwer=query.getString(2);
                    int type=query.getInt(3);
                    int id=query.getInt(0);
                    mas.add(new TaskInfoNow(anwer,task));
                    mas.get(mas.size()-1).type=type;
                    mas.get(mas.size()-1).id=id;

                }
                while (query.moveToNext());
            }
            query.close();
//            Callback.Calls.database.close();
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mTask2 = null;
            int u=0;
            if (success) {
                adapter.tasks.clear();
                adapter.tasks.addAll(mas);
                adapter.notifyDataSetChanged();
            } else {
            }
        }
    }
}
