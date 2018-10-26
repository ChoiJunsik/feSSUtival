package com.example.junsikchoi.ssu_festival;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class storeDetail extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener{

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mUserToken;
    private DatabaseReference mFirebaseDatabaseReference;
    Fragment fragmentThree;
    Fragment fragmentOne;
    String storeName;
    ArrayList<String> amStoreList = new ArrayList<>();
    ArrayList<String> pmStoreList = new ArrayList<>();

    ViewPager viewPager;
    ImageView settingImage;
    ImageView orderButton;
    TextView settingText;
    TextView waitCnt2;
    Integer[]amImageId = {
            R.drawable.am0,
            R.drawable.am1,
            R.drawable.am2,
            R.drawable.am3,
            R.drawable.am4,
            R.drawable.am5
    };

    Integer[]pmImageId = {
            R.drawable.pm0,
            R.drawable.pm1,
            R.drawable.pm2,
            R.drawable.pm3,
            R.drawable.pm4,
            R.drawable.pm5
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        amStoreList.add("Berry Gut");
        amStoreList.add("유어슈");
        amStoreList.add("일벌렸SSU");
        amStoreList.add("한끼줍SSU");
        amStoreList.add("한주먹거리");
        amStoreList.add("호도깨비야시장");

        pmStoreList.add("소프트웨어학부 주점");
        pmStoreList.add("언론홍보학과 주점");
        pmStoreList.add("경통대 주점");
        pmStoreList.add("글로벌미디어학과 주점");
        pmStoreList.add("물리학과 주점");
        pmStoreList.add("자연과학대 주점");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        setContentView(R.layout.activity_store_detail);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();
        mUserToken = FirebaseInstanceId.getInstance().getToken();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        orderButton = (ImageView)findViewById(R.id.order_button);
        settingImage = (ImageView)findViewById(R.id.detail_img);
        settingText = (TextView)findViewById(R.id.fragment_two);
        waitCnt2 = (TextView)findViewById(R.id.waitCnt);
        storeName = intent.getStringExtra("storeName");

        fragmentThree = new PageThreeFragment(); // Fragment 생성
        fragmentOne = new PageOneFragment();
        Bundle bundle = new Bundle(3); // 파라미터는 전달할 데이터 개수
        bundle.putString("storeName", storeName); // key , value
        bundle.putString("userName",mUsername);
        bundle.putInt("imageId",intent.getIntExtra("imgId",0));
        fragmentThree.setArguments(bundle);
        fragmentOne.setArguments(bundle);
        if(amStoreList.contains(storeName)){
            settingImage.setImageResource(amImageId[intent.getIntExtra("imgId",0)]);}
        else
            settingImage.setImageResource(pmImageId[intent.getIntExtra("imgId",0)]);

        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        mFirebaseDatabaseReference.child("Store").child(storeName).child("waitCnt").addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(DataSnapshot snapshot){
                waitCnt2.setText(""+snapshot.getValue());
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        orderButton.setOnClickListener(new View.OnClickListener() {

            DatabaseReference cnt = mFirebaseDatabaseReference.child("Store").child(storeName).child("cnt");
            DatabaseReference waitCnt = mFirebaseDatabaseReference.child("Store").child(storeName).child("waitCnt");
            DatabaseReference userCheck = mFirebaseDatabaseReference.child("Users").child(mUsername).child("number");
            @Override
            public void onClick(View v) {
                if(amStoreList.contains(mUsername)||pmStoreList.contains(mUsername)){
                    Toast.makeText(getApplicationContext(), "점포용 아이디로는 예약할 수 없습니다.", Toast.LENGTH_LONG).show();

                    return;}
                userCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Long check = snapshot.getValue(Long.class);
                        if(check==null){
                                cnt.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    Long cnt = (Long)snapshot.getValue();
                                    Map<String, Object> childUpdates;
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/number/",cnt);
                                    mFirebaseDatabaseReference.child("Users").child(mUsername).updateChildren((childUpdates));
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/store/",storeName);
                                    mFirebaseDatabaseReference.child("Users").child(mUsername).updateChildren((childUpdates));
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/token/",mUserToken);
                                    mFirebaseDatabaseReference.child("Users").child(mUsername).updateChildren((childUpdates));
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/token/",mUserToken);
                                    mFirebaseDatabaseReference.child("Store").child(storeName).child("Users").child(mUsername).updateChildren((childUpdates));
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/cnt/",cnt);
                                    mFirebaseDatabaseReference.child("Store").child(storeName).child("Users").child(mUsername).updateChildren((childUpdates));
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/name/",mUsername);
                                    mFirebaseDatabaseReference.child("Store").child(storeName).child("Users").child(mUsername).updateChildren((childUpdates));
                                    ++cnt;
                                    childUpdates = new HashMap<>();
                                    childUpdates.put("/cnt/", cnt);
                                    mFirebaseDatabaseReference.child("Store").child(storeName).updateChildren(childUpdates);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                            waitCnt.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    Long cnt = (Long)snapshot.getValue();
                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("/waitCnt/",++cnt);
                                    mFirebaseDatabaseReference.child("Store").child(storeName).updateChildren(childUpdates);
                                    waitCnt2.setText(""+cnt);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                            Toast.makeText(getApplicationContext(), "성공적으로 예약되었습니다.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "이미 예약된 번호표가 있습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });
            }
        });
    }


    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }
    public void onTabUnselected(TabLayout.Tab tab) {
    }
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public void onClick(View v) {
    }

    class PageAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments=new ArrayList<>();
        String title[]=new String[]{"메뉴", "리뷰"};
        public PageAdapter(FragmentManager fm){
            super(fm);
            fragments.add(fragmentOne);
           // fragments.add(new PageTwoFragment());
            fragments.add(fragmentThree);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}

