package com.psuclubapp.psuclubapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ShowClubFragment extends Fragment {

    public TextView nameWelcome;
    public LinearLayout club_top;
    public LinearLayout club_bottom_r;

    public String username_sp = null;
    public Integer role_sp = 0;
    public String nameStd_sp = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_club, container, false);

        nameWelcome = (TextView) view.findViewById(R.id.nameWelcome);
        club_top = (LinearLayout) view.findViewById(R.id.club_top);
        club_bottom_r = (LinearLayout) view.findViewById(R.id.club_bottom_r);

        //ดึงข้อมูลของ User จากหน้า log in โดยไม่ใช่ intent
        SharedPreferences sp = this.getActivity().getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        role_sp = sp.getInt("dataRole", 0);
        nameStd_sp = sp.getString("dataNameStd", null);


        if(role_sp == 1){
            nameWelcome.setText(username_sp);
            club_top.setVisibility(View.GONE);
            club_bottom_r.setVisibility(View.GONE);
        }
        else {
            nameWelcome.setText(nameStd_sp);
        }

        ImageView Head = (ImageView) view.findViewById(R.id.club_head);
        Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ClubHeadActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        ImageView Me = (ImageView) view.findViewById(R.id.club_me);
        Me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ClubMeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ImageView Follow = (ImageView) view.findViewById(R.id.club_follow);
        Follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ClubFollowActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ImageView All = (ImageView) view.findViewById(R.id.club_all);
        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ClubAllActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        if (role_sp == 1){
            Head.setVisibility(View.GONE);
            Me.setVisibility(View.GONE);
            Follow.setVisibility(View.GONE);
        }

        return view;
    }

    //setListViewHeightBasedOnChildren(listViewFollow);  //ใช้เรียก setListViewHeightBasedOnChildren
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

}
