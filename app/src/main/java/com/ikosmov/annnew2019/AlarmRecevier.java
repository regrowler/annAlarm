package com.ikosmov.annnew2019;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.putExtras(intent.getExtras());
        i.setClassName("com.ikosmov.annnew2019", "com.ikosmov.annnew2019.Main3Activity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

//и тут типо задачи еще и звуковой сигнал
    }
}