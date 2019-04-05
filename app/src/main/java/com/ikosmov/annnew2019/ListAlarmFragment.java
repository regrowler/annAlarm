package com.ikosmov.annnew2019;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListAlarmFragment extends Fragment {
    RecyclerView recyclerView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_list_fragment, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=getActivity().findViewById(R.id.mainlist);
        ArrayList<AlarmInfoNow> mas=new ArrayList<>();
        mas.add(new AlarmInfoNow());
        recyclerView.setAdapter(new ListViewAdapter(getContext(),mas));
    }
}
