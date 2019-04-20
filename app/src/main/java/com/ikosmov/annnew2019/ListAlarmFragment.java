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

import java.util.ArrayList;
//фрагмент-главный экран с будильниками
public class ListAlarmFragment extends Fragment {
    RecyclerView recyclerView;
    ListViewAdapter adapter;
    private Task mAuthTask;
    ArrayList<AlarmInfoNow> mas=new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_list_fragment, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //инициализация списка
        recyclerView=getActivity().findViewById(R.id.mainlist);
        getActivity().setTitle("Список будильников");
        adapter=new ListViewAdapter(getContext(),mas);
        //mas.add(new AlarmInfoNow());
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),Main2Activity.class);
                getActivity().startActivity(i);
            }
        });
        mAuthTask=new Task();
        mAuthTask.execute((Void)null);
    }
    //обновить список будильников
    public void update(){
        adapter.notifyDataSetChanged();
        int u=0;
    }
    //ассинхронное задание которое
    //в отдельном потоке обновляет список
    public class Task extends AsyncTask<Void, Void, Boolean> {
        private final ArrayList<AlarmInfoNow> mas;
        public Task(){mas=new ArrayList<>();}

        @Override
        protected Boolean doInBackground(Void... voids) {
            //проверка существовния таблицы
            Callback.Calls.database.execSQL("CREATE TABLE IF NOT EXISTS alarms (" +
                    "ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
                    "year INTEGER," +
                    "month INTEGER," +
                    "day INTEGER," +
                    "hour INTEGER," +
                    "minute INTEGER," +
                    "ison INTEGER," +
                    "task INTEGER," +
                    "sound INTEGER);");
//            Callback.Calls.database.execSQL("INSERT INTO user(name,age) VALUES ('pidr', 23);");
//            Callback.Calls.database.execSQL("INSERT INTO user(name,age) VALUES ('too', 31);");
//            SQLiteStatement s=db.compileStatement("SELECT * FROM user where id=?;");
//            Cursor query=s.
            Cursor query = Callback.Calls.database.rawQuery("SELECT * FROM alarms ;", new String[]{});
            if (query.moveToFirst()) {
                do {

                    int year = query.getInt(1);
                    int month=query.getInt(2);
                    int day=query.getInt(3);
                    int hour=query.getInt(4);
                    int minute=query.getInt(5);
                    int ison=query.getInt(6);
                    int task=query.getInt(7);
                    int sound=query.getInt(8);
                    int id=query.getInt(0);
                    mas.add(new AlarmInfoNow(year,
                            month,
                            day,
                            hour,
                            minute,
                            task,
                            sound));
                    if(ison==1){
                        mas.get(mas.size()-1).isOn=true;
                    }else if(ison==0){
                        mas.get(mas.size()-1).isOn=false;
                    }
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
            mAuthTask = null;
            //обновления списка
            if (success) {
                adapter.alarms.clear();
                adapter.alarms.addAll(mas);
                adapter.notifyDataSetChanged();
            } else {
            }
        }
    }

}
