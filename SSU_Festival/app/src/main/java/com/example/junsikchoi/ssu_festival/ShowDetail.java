package com.example.junsikchoi.ssu_festival;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button[] buttons = {button, button2, button3};

        DateClickListener dateClickListener = new DateClickListener();
        for(int i=0; i<buttons.length; i++){
            buttons[i].setOnClickListener(dateClickListener);
        }
    }

    private class DateClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ShowMainFragment.class);
            switch (v.getId()){
                case R.id.button : intent.putExtra("num", 0); break;
                case R.id.button2 : intent.putExtra("num", 1); break;
                case R.id.button3 : intent.putExtra("num", 2); break;
            }
            startActivity(intent);
        }
    }
}