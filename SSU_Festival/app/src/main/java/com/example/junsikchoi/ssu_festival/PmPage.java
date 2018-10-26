package com.example.junsikchoi.ssu_festival;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PmPage extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ArrayList<AmPmVO> datas;
    String [] name = {"소프트웨어학부 주점","언론홍보학과 주점","경통대 주점","글로벌미디어학과 주점","물리학과 주점","자연과학대 주점"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_page);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);

        ListView listView = (ListView) findViewById(R.id.pm_list);
        listView.setOnItemClickListener(this);
        datas = new ArrayList<>();

        PmDB helper = new PmDB(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name, detail from tb_pm", null);
        while (cursor.moveToNext()) {
            AmPmVO vo = new AmPmVO();
            vo.name = cursor.getString(0);
            vo.detail = cursor.getString(1);
            datas.add(vo);
        }
        db.close();

        PmAdapter adapter = new PmAdapter(this, R.layout.ampm_list_item, datas);
        listView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,storeDetail.class);
        intent.putExtra("imgId",position);
        intent.putExtra("storeName",name[position]);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}