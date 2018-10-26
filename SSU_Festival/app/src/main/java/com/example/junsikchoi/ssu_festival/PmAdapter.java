package com.example.junsikchoi.ssu_festival;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PmAdapter extends ArrayAdapter<AmPmVO> {

        Context context;
        int resId;
        ArrayList<AmPmVO> datas;
        Integer[]imageId = {
        R.drawable.pm_mini_0,
        R.drawable.pm_mini_1,
        R.drawable.pm_mini_2,
        R.drawable.pm_mini_3,
        R.drawable.pm_mini_4,
        R.drawable.pm_mini_5,
        };
public PmAdapter(Context context, int resId, ArrayList<AmPmVO> datas){
        super(context, resId);
        this.context=context;
        this.resId=resId;
        this.datas=datas;
        }

@Override
public int getCount() {
        return datas.size();
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(resId, null);

        ampmWrapper wrapper=new ampmWrapper(convertView);
        convertView.setTag(wrapper);
        }

        ampmWrapper wrapper=(ampmWrapper)convertView.getTag();

        ImageView imgView=wrapper.imgView;
        TextView nameView=wrapper.nameView;
        TextView detailView=wrapper.detailView;
        ImageView arrowImageView=wrapper.arrowImageView;

final AmPmVO vo=datas.get(position);

        nameView.setText(vo.name);
        detailView.setText(vo.detail);
        imgView.setImageResource(imageId[position]);
        arrowImageView.setImageResource(R.drawable.arrow);

        return convertView;
        }

        }

