package com.ikosmov.annnew2019;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewH>{

    List<AlarmInfoNow> alarms;
    Context context; //
    LayoutInflater inflater; //выводит элемент

    public  ListViewAdapter(Context mycontext, List<AlarmInfoNow> mas){
        this.context = mycontext;
        inflater = LayoutInflater.from(context);
        alarms=mas;
    }

    @NonNull
    @Override// далее чисто настройки по карточке
    public ViewH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recylercard, viewGroup, false);
        return new ViewH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewH viewH, int i) { //переписывает карточку вместо создания новой, в отличии от лист вью
        viewH.label.setText("text");
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public class ViewH extends RecyclerView.ViewHolder{
        Switch switch1=null;
        TextView label=null;
        RelativeLayout general=null;
        public ViewH(@NonNull android.view.View itemView) {
            super(itemView);
            switch1=itemView.findViewById(R.id.itemswitch);
            label=itemView.findViewById(R.id.itemlabel);
            general=itemView.findViewById(R.id.itemgeneral);
        }
    }
}
