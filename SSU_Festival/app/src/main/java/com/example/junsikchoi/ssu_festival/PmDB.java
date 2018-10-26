package com.example.junsikchoi.ssu_festival;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PmDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=2;

    public PmDB(Context context){
        super(context, "pmdb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableSql="create table tb_pm (_id integer primary key autoincrement, name, detail)";

        db.execSQL(tableSql);
        db.execSQL("insert into tb_pm (name,detail) values ('소프트웨어학부 주점','감바스, 요구르트 액상')");
        db.execSQL("insert into tb_pm (name,detail) values ('언론홍보학과 주점','오돌뼈와 주먹밥, 나가사끼 짬뽕')");
        db.execSQL("insert into tb_pm (name,detail) values ('경통대 주점','순대볶음, 소세지 야채볶음')");
        db.execSQL("insert into tb_pm (name,detail) values ('글로벌미디어학과 주점','불타는 제육뽀오끔, 오뎅탕야탕야')");
        db.execSQL("insert into tb_pm (name,detail) values ('물리학과 주점','대패삼겹숙주볶음, 닭꼬치 염통꼬치')");
        db.execSQL("insert into tb_pm (name,detail) values ('자연과학대 주점','닭발, 나쵸')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table tb_pm");
            onCreate(db);
        }
    }

}
