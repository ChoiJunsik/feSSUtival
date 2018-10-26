package com.example.junsikchoi.ssu_festival;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private GoogleApiClient mGoogleApiClient;

    // 사용자 이름과 사진
    private String mUsername;
    private String mPhotoUrl;
    //판매점포아이디
    ArrayList<String> storeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        storeList.add("Berry Gut");
        storeList.add("유어슈");
        storeList.add("일벌렸SSU");
        storeList.add("한끼줍SSU");
        storeList.add("한주먹거리");
        storeList.add("호도깨비야시장");

        storeList.add("소프트웨어학부 주점");
        storeList.add("언론홍보학과 주점");
        storeList.add("경통대 주점");
        storeList.add("글로벌미디어학과 주점");
        storeList.add("물리학과 주점");
        storeList.add("자연과학대 주점");


        mUsername = mFirebaseUser.getDisplayName();
        if (mFirebaseUser == null) {
            // 인증이 안 되었다면 인증 화면으로 이동
            startActivity(new Intent(this, LoadingActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();

            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        ImageView amButton = (ImageView)findViewById(R.id.amButton);
        ImageView pmButton = (ImageView)findViewById(R.id.pmButton);
        ImageView numberButton = (ImageView)findViewById(R.id.numberButton);
        ImageView showButton = (ImageView)findViewById(R.id.showButton);
        ImageView noticeButton = (ImageView)findViewById(R.id.noticeButton);
        ImageView qnaButton = (ImageView)findViewById(R.id.qnaButton);
        amButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AmPage.class);
                startActivityForResult(intent,10);
            }
        });

        pmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PmPage.class);
                startActivityForResult(intent,10);
            }
        });
        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeList.contains(mUsername)){
                     Intent intent = new Intent(v.getContext(),NumberCurrentForStore.class);
                     startActivity(intent);}
                else{
                    Intent intent = new Intent(v.getContext(),NumberCurrentForUser.class);
                    startActivity(intent);
                }
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ShowDetail.class);
                startActivity(intent);
            }
        });

        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),NoticePage.class);
                startActivity(intent);
            }
        });

        qnaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Qna.class);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode==10 && resultCode == RESULT_OK){}

    }
}
