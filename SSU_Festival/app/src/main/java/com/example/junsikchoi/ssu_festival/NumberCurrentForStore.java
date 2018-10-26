package com.example.junsikchoi.ssu_festival;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Callback;
import okhttp3.Response;

public class NumberCurrentForStore extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference userDB;
    TextView storeName;
    TextView waitCnt;
    TextView callCnt;
    TextView callName;
    Button callButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_current_for_store);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        storeName = (TextView)findViewById(R.id.storeName);
        waitCnt = (TextView)findViewById(R.id.waitCnt);
        callCnt = (TextView)findViewById(R.id.callCnt);
        callName = (TextView)findViewById(R.id.callName);
        callButton = (Button)findViewById(R.id.callButton);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        userDB = mFirebaseDatabaseReference.child("Store").child(mUsername);
        storeName.setText(mUsername);
        DatabaseReference storeDB = mFirebaseDatabaseReference.child("Store").child(mUsername);

        storeDB.child("Users").orderByChild("cnt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Long cnt = (Long)postSnapshot.child("cnt").getValue();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/callCnt/",cnt);
                    userDB.updateChildren(childUpdates);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        loadingDB();
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = callName.getText().toString();

                mFirebaseDatabaseReference.child("Store").child(mUsername).child("Users").child(userName).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        sendGcm((String)snapshot.getValue());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                DatabaseReference waitCnt = mFirebaseDatabaseReference.child("Store").child(mUsername).child("waitCnt");
                waitCnt.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Long cnt = (Long)snapshot.getValue();
                        Map<String, Object> childUpdates = new HashMap<>();
                        if(cnt>0)
                            --cnt;
                        childUpdates.put("/waitCnt/",cnt);
                        mFirebaseDatabaseReference.child("Store").child(mUsername).updateChildren(childUpdates);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mFirebaseDatabaseReference.child("Store").child(mUsername).child("Users").child(userName).setValue(null);
                    }
                }, 1000);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        loadingDB();
                    }
                }, 2000);
                Toast.makeText(getApplicationContext(), "호출되었습니다.", Toast.LENGTH_LONG).show();
                callButton.setVisibility(View.INVISIBLE);
            }
        });

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
        userDB.child("waitCnt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Long cnt = (Long)snapshot.getValue();
                waitCnt.setText(""+cnt);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        userDB.child("Users").orderByChild("cnt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue()==null){
                    callCnt.setText("");
                    callName.setText("");
                }
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String name = postSnapshot.child("name").getValue().toString();
                    Long cnt = (Long)postSnapshot.child("cnt").getValue();
                    callCnt.setText(""+cnt);
                    callName.setText(name);
                    callButton.setVisibility(View.VISIBLE);
                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    void sendGcm(String token){

        Gson gson = new Gson();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = token;
        notificationModel.notification.title = mUsername;
        notificationModel.notification.text = "예약 호출입니다!";


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(notificationModel));

        Request request = new Request.Builder()
                .header("Content-Type","application/json")
                .addHeader("Authorization","key=AIzaSyDNKC662OUx2qJCFAYEZQ76HF7_noNwxCQ")
                .url("https://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }

}
