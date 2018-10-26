package com.example.junsikchoi.ssu_festival;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.junsikchoi.ssu_festival.R;

import java.util.ArrayList;
public class ShowPagerAdapter  extends FragmentPagerAdapter {
    private ArrayList<Fragment> mData;

    public ShowPagerAdapter(FragmentManager fm) {
        super(fm);
        mData = new ArrayList<>();
        mData.add(new Day1Fragment());
        mData.add(new Day2Fragment());
        mData.add(new Day3Fragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    public static class Day3Fragment extends Fragment {

        public Day3Fragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_day3, container, false);
        }
    }

    public static class Day1Fragment extends Fragment {

        public Day1Fragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_day1, container, false);
        }
    }

    public static class Day2Fragment extends Fragment {

        public Day2Fragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_day2, container, false);
        }
    }
}



