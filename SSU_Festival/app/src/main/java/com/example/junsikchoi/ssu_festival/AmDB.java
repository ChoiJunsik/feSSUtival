package com.example.junsikchoi.ssu_festival;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AmDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=2;

    public AmDB(Context context){
        super(context, "amdb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableSql="create table tb_am (_id integer primary key autoincrement, name, detail)";

        db.execSQL(tableSql);
        db.execSQL("insert into tb_am (name,detail) values ('Berry Gut','에이드(오미자,복분자,오디)')");
        db.execSQL("insert into tb_am (name,detail) values ('유어슈','복숭아 음료, 타로점')");
        db.execSQL("insert into tb_am (name,detail) values ('일벌렸SSU','스떡스떡, 에이드')");
        db.execSQL("insert into tb_am (name,detail) values ('한끼줍SSU','대패 삼겹 숙주 볶음')");
        db.execSQL("insert into tb_am (name,detail) values ('한주먹거리','슬라임, 복숭아청')");
        db.execSQL("insert into tb_am (name,detail) values ('호도깨비야시장','옥수수전, 부추전')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table tb_am");
            onCreate(db);
        }
    }

}
