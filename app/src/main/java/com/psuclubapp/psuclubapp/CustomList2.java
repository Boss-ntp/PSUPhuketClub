package com.psuclubapp.psuclubapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NattapongNm on 17-Jun-16.
 */
public class CustomList2 extends BaseAdapter {

    private Context con;
    private ArrayList<String> data_subAct;
    private ArrayList<String> data_detail;
    private ArrayList<String> data_id_act;

    public CustomList2(Context con, ArrayList<String> subAct,ArrayList<String> detail, ArrayList<String> id_act)
    {
        this.con = con;
        this.data_subAct = subAct;
        this.data_detail = detail;
        this.data_id_act = id_act;

    }

    @Override
    public int getCount() { //มีกี่ตัว
        return data_subAct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //วนตามจำนวนที่นับได้
        LayoutInflater lay = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView  =  lay.inflate(R.layout.custom_list2,null);
            TextView textView = (TextView)convertView.findViewById(R.id.subAct);
            TextView textView2 = (TextView)convertView.findViewById(R.id.detailAct);
            ImageView picAct = (ImageView)convertView.findViewById(R.id.picAct);
            textView.setText(data_subAct.get(position));
            textView2.setText(data_detail.get(position));

        String array[] = new String[data_id_act.size()];
        for(int j =0;j<data_id_act.size();j++){
            array[j] = data_id_act.get(position);
            switch (array[j]){
                case "15": picAct.setImageResource(R.drawable.a15); break;
                case "16": picAct.setImageResource(R.drawable.a16); break;
                case "29": picAct.setImageResource(R.drawable.a29); break;
                case "30": picAct.setImageResource(R.drawable.a30); break;
                case "31": picAct.setImageResource(R.drawable.a31); break;
                case "32": picAct.setImageResource(R.drawable.a32); break;
                case "33": picAct.setImageResource(R.drawable.a33); break;
                default: picAct.setImageResource(R.drawable.logo);
            }
        }

        return convertView;
    }
}
