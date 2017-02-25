package com.psuclubapp.psuclubapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ClubActFragment extends Fragment {

    public TextView nameWelcome;
    public String username_sp = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_act, container, false);

        nameWelcome = (TextView) view.findViewById(R.id.nameWelcome);

        SharedPreferences sp = this.getActivity().getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);

        nameWelcome.setText(username_sp);

        ImageView clubAll = (ImageView) view.findViewById(R.id.club_all);
        clubAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ActAllActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ImageView actAll = (ImageView) view.findViewById(R.id.act_all);
        actAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ClubAllActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }


}
