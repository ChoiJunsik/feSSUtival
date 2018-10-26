package com.example.junsikchoi.ssu_festival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageOneFragment extends Fragment {

    Integer menuId;
    String storeName;
    ImageView img;

    ArrayList<String> amStoreList = new ArrayList<>();
    ArrayList<String> pmStoreList = new ArrayList<>();

    Integer[]amMenuImgId = {
            R.drawable.am0_menu,
            R.drawable.am1_menu,
            R.drawable.am2_menu,
            R.drawable.am3_menu,
            R.drawable.am4_menu,
            R.drawable.am5_menu
    };
    Integer[]pmMenuImgId = {
            R.drawable.pm0_menu,
            R.drawable.pm1_menu,
            R.drawable.pm2_menu,
            R.drawable.pm3_menu,
            R.drawable.pm4_menu,
            R.drawable.pm5_menu
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (LinearLayout)inflater.inflate(R.layout.fragment_page_one, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

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

        Bundle bundle=getArguments();

        menuId = new Integer(bundle.getInt("imageId"));
        storeName = bundle.getString("storeName");

        img = getView().findViewById(R.id.fragment_one);

        if(amStoreList.contains(storeName))
            img.setImageResource(amMenuImgId[menuId]);
        else
            img.setImageResource(pmMenuImgId[menuId]);


    }

}
