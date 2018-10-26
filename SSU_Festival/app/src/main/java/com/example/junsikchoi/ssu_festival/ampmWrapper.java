package com.example.junsikchoi.ssu_festival;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ampmWrapper {
    public ImageView imgView;
    public TextView nameView;
    public TextView detailView;
    public ImageView arrowImageView;
    public ampmWrapper(View root){
        imgView=(ImageView)root.findViewById(R.id.ampm_item_img);
        nameView=(TextView)root.findViewById(R.id.ampm_item_name);
        detailView=(TextView)root.findViewById(R.id.ampm_item_detail);
        arrowImageView=(ImageView)root.findViewById(R.id.ampm_item_arrow);
    }
}
