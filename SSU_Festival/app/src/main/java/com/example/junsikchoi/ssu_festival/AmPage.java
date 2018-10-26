package com.example.junsikchoi.ssu_festival;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AmPage extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ArrayList<AmPmVO> datas;
    String [] name = {"Berry Gut","유어슈","일벌렸SSU","한끼줍SSU","한주먹거리","호도깨비야시장"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_page);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);

        ListView listView = (ListView) findViewById(R.id.am_list);
        listView.setOnItemClickListener(this);
        datas = new ArrayList<>();

        AmDB helper = new AmDB(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name, detail from tb_am", null);
        while (cursor.moveToNext()) {
            AmPmVO vo = new AmPmVO();
            vo.name = cursor.getString(0);
            vo.detail = cursor.getString(1);
            datas.add(vo);
        }
        db.close();

        AmAdapter adapter = new AmAdapter(this, R.layout.ampm_list_item, datas);
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
