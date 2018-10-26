package com.example.junsikchoi.ssu_festival;

import android.os.Handler;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NumberCurrentForUser extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference userDB;
    TextView storeName;
    TextView waitCnt;
    TextView callCnt;
    TextView myCnt;
    ImageView storeImg;
    ArrayList<Pair<String,Integer>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_current_for_user);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        list  = new ArrayList <>();

        list.add(new Pair("Berry Gut",R.drawable.am_mini_0));
        list.add(new Pair("유어슈",R.drawable.am_mini_1));
        list.add(new Pair("일벌렸SSU",R.drawable.am_mini_2));
        list.add(new Pair("한끼줍SSU",R.drawable.am_mini_3));
        list.add(new Pair("한주먹거리",R.drawable.am_mini_4));
        list.add(new Pair("호도깨비야시장",R.drawable.am_mini_5));
        list.add(new Pair("소프트웨어학부 주점",R.drawable.pm_mini_0));
        list.add(new Pair("언론홍보학과 주점",R.drawable.pm_mini_1));
        list.add(new Pair("경통대 주점",R.drawable.pm_mini_2));
        list.add(new Pair("글로벌미디어학과 주점",R.drawable.pm_mini_3));
        list.add(new Pair("물리학과 주점",R.drawable.pm_mini_4));
        list.add(new Pair("자연과학대 주점",R.drawable.pm_mini_5));

        storeName = (TextView)findViewById(R.id.storeName);
        waitCnt = (TextView)findViewById(R.id.waitCnt);
        callCnt = (TextView)findViewById(R.id.callCnt);
        myCnt = (TextView)findViewById(R.id.myCnt);
        storeImg = (ImageView)findViewById(R.id.storeImg);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        userDB = mFirebaseDatabaseReference.child("Users").child(mUsername);

        loadingDB();

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
    void loadingDB(){
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final Long myCntDB = (Long)snapshot.child("number").getValue();
                final String storeNameDB = (String)snapshot.child("store").getValue();
                for(Pair<String,Integer> forImg : list){
                    if(forImg.first.equals(storeNameDB)) {
                        storeImg.setImageResource(forImg.second);
                        break;
                    }
                }
                mFirebaseDatabaseReference.child("Store").child(mUsername).child("Users").orderByChild("cnt").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Long cnt = (Long)postSnapshot.child("cnt").getValue();

                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/callCnt/",cnt);
                            mFirebaseDatabaseReference.child("Store").child(storeNameDB).updateChildren(childUpdates);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                if(myCntDB==null){
                    return;
                }
                myCnt.setText(""+myCntDB);
                storeName.setText(storeNameDB);
                DatabaseReference storeDB = mFirebaseDatabaseReference.child("Store").child(storeNameDB);
                storeDB.child("waitCnt").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long cnt = (Long)dataSnapshot.getValue();
                        waitCnt.setText(""+cnt);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                ///호출 누를시 유저 정보 사라져서 call cnt 가 안나오는 BUG
                storeDB.child("Users").orderByChild("cnt").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Long cnt = (Long)postSnapshot.child("cnt").getValue();
                            callCnt.setText(""+cnt);
                            break;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
