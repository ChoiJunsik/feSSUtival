package com.example.junsikchoi.ssu_festival;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> ListTitle;
    private LinkedHashMap<String, List<String>> ListDetail;
    private int[] imgs = new int[]{R.drawable.no1, R.drawable.no2, R.drawable.no3,
            R.drawable.no4, R.drawable.no5, R.drawable.no6, R.drawable.no7, R.drawable.no8};
    private int[] imgs2 = new int[]{R.drawable.no1_1, 0, 0, 0, 0, 0, 0,R.drawable.no8_1};

    public ExpandableListAdapter(Context context, List<String> ListTitle, LinkedHashMap<String, List<String>> ListDetail){
        super();
        this.context = context;
        this.ListTitle = ListTitle;
        this.ListDetail = ListDetail;
    }

    @Override
    public int getGroupCount() {
        return ListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return 1;   //밑에 보여줄 리스트 1개 고정
        return ListDetail.get(ListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return ListDetail.get(groupPosition);
        return ListDetail.get(ListTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String)getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView)convertView.findViewById(R.id.listTitle);

        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String listText = (String)getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        ImageView listImageView = (ImageView)convertView.findViewById(R.id.listImage);
        ImageView listImageView2 = (ImageView)convertView.findViewById(R.id.listImage2);
        listImageView.setImageResource(imgs[groupPosition]);
        listImageView2.setImageResource(imgs2[groupPosition]);

        TextView listTextView = (TextView)convertView.findViewById(R.id.listItem);
        listTextView.setText(listText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}