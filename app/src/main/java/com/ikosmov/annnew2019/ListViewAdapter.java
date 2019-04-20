package com.ikosmov.annnew2019;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
//адаптер списка будильников
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewH> {

    List<AlarmInfoNow> alarms;
    Context context; //
    LayoutInflater inflater; //выводит элемент
    boolean swcheck = false;

    public ListViewAdapter(Context mycontext, List<AlarmInfoNow> mas) {
        this.context = mycontext;
        inflater = LayoutInflater.from(context);
        alarms = mas;
    }

    @NonNull
    @Override// далее чисто настройки по карточке
    public ViewH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recylercard, viewGroup, false);
        return new ViewH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewH viewH, int j) { //переписывает карточку вместо создания новой, в отличии от лист вью
        StringBuilder stringBuilder=new StringBuilder();
        if(Callback.Calls.main.listAlarmFragment.mas.get(j).hour < 10)
        {
            stringBuilder.append("0");
        }
        stringBuilder.append(Callback.Calls.main.listAlarmFragment.mas.get(j).hour);
        stringBuilder.append(":");
        if(Callback.Calls.main.listAlarmFragment.mas.get(j).minute < 10)
        {
            stringBuilder.append("0");
        }
        stringBuilder.append(Callback.Calls.main.listAlarmFragment.mas.get(j).minute);
        viewH.label.setText(stringBuilder.toString());
        final int t = j;
        AlarmInfoNow infoNow=Callback.Calls.main.listAlarmFragment.adapter.alarms.get(t);
        if(infoNow.isOn){
            viewH.switch1.setChecked(true);
        }else viewH.switch1.setChecked(false);

        viewH.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Callback.Calls.main.switchT(t);

            }
        });
        //вызов экрана просмотра свойств будильника
        viewH.general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Main2Activity.class);
                i.putExtra("id", t + "");
                i.putExtra("year",alarms.get(t).year+"");
                i.putExtra("month",alarms.get(t).month+"");
                i.putExtra("day",alarms.get(t).day+"");
                i.putExtra("hour", alarms.get(t).hour + "");
                i.putExtra("minute", alarms.get(t).minute + "");
                i.putExtra("task", alarms.get(t).typetask + "");
                i.putExtra("ring", alarms.get(t).typering + "");
                i.putExtra("number", alarms.get(t).id + "");
                context.startActivity(i);
            }
        });

        viewH.general.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPopupMenu(view, t);
                return false;
            }
        });
        viewH.switch1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //alarms.get(t).isOn = b;
            }
        });

    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public class ViewH extends RecyclerView.ViewHolder {
        Switch switch1 = null;
        TextView label = null;
        RelativeLayout general = null;

        public ViewH(@NonNull android.view.View itemView) {
            super(itemView);
            switch1 = itemView.findViewById(R.id.itemswitch);
            label = itemView.findViewById(R.id.itemlabel);
            general = itemView.findViewById(R.id.itemgeneral);

        }
    }
    //показать всплывающее меню
    private void showPopupMenu(View v, final int pos) {
        PopupMenu popupMenu = new PopupMenu(context, v, Gravity.TOP);
        popupMenu.inflate(R.menu.popupmenu);
        final  int i=pos;
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.PopupClear:
                                Callback.Calls.main.turnOf(pos);
                                Callback.Calls.database.delete("alarms","ID=?",new String[]{alarms.get(i).id+""});
                                alarms.remove(i);
                                notifyDataSetChanged();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        popupMenu.show();
    }

}
