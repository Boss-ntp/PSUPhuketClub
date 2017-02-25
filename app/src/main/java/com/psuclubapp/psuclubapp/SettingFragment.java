package com.psuclubapp.psuclubapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    public static Integer role_sp = 0;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        final SharedPreferences sp = this.getActivity().getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        editor = sp.edit();
        role_sp = sp.getInt("dataRole",0);

        ImageView img = (ImageView) view.findViewById(R.id.register);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),RegisAppActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        ImageView img2 = (ImageView) view.findViewById(R.id.profile);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ProfileActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        ImageView img3 = (ImageView) view.findViewById(R.id.logOut);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),LoginActivity.class);
                editor.clear();
                editor.commit();
                startActivity(intent);
                getActivity().finish();
            }
        });

        if(role_sp == 1){ //ถ้าเป็นแอดมิน จะไม่สามารถเเก้ไขข้อมูลส่วนตัวของตัวเองได้ เพราะไม่มีการเก็บข้อมูลส่วนตัว
//            register.setVisibility(View.GONE);
//            profile.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
        };


        return view;
    }



}
