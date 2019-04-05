package com.ikosmov.annnew2019;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

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
    public void onBindViewHolder(@NonNull ViewH viewH, int i) {

    }

    @Override
    public int getItemCount() {
         return tasks.size();
    }
    public class ViewH extends RecyclerView.ViewHolder{
        TextView label=null;
        RelativeLayout general=null;
        public ViewH(@NonNull android.view.View itemView) {
            super(itemView);
            label=itemView.findViewById(R.id.itemlabel);
            general=itemView.findViewById(R.id.itemgeneral);
        }
    }
}
