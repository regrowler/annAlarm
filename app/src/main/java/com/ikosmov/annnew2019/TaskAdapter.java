package com.ikosmov.annnew2019;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
//адаптер списка задач
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewH> {


    List<TaskInfoNow> tasks;
    Context context; //
    LayoutInflater inflater; //выводит элемент

    public  TaskAdapter (Context mycontext, List<TaskInfoNow> mas){
        this.context = mycontext;
        inflater = LayoutInflater.from(context);
        tasks = mas;
    }



    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.taskcard, viewGroup, false);
        return new ViewH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewH viewH, final int i) {
        viewH.task.setText(tasks.get(i).task);
        viewH.answer.setText(tasks.get(i).answer);
        viewH.general.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPopupMenu(view,i);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
         return tasks.size();
    }
    public class ViewH extends RecyclerView.ViewHolder{
        TextView task=null;
        TextView answer=null;
        LinearLayout general=null;
        public ViewH(@NonNull android.view.View itemView) {
            super(itemView);
            task=itemView.findViewById(R.id.itemtask);
            answer = itemView.findViewById(R.id.itemanswer);
            general=itemView.findViewById(R.id.general);
        }
    }
    //показать всплвывающее меню
    private void showPopupMenu(View v,int pos) {
        PopupMenu popupMenu = new PopupMenu(context, v, Gravity.TOP);
        popupMenu.inflate(R.menu.popupmenu);
        final int f=pos;
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.PopupClear:
                                try {
                                    Callback.Calls.database.delete("tasks","ID=?",new String[]{tasks.get(f).id+""});
                                    tasks.remove(f);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
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
