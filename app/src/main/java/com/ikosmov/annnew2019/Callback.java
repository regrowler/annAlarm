package com.ikosmov.annnew2019;

import android.database.sqlite.SQLiteDatabase;
//репозиторий приложения
//упрощает связь между компонентами
public class Callback {
    public static class Calls{
        public static Drawer main=null;
        public static SQLiteDatabase database;
    }
}
