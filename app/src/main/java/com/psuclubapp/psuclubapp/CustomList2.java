package com.psuclubapp.psuclubapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
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
    private ArrayList<String> data_picAct;

    public CustomList2(Context con, ArrayList<String> subAct, ArrayList<String> detail, ArrayList<String> id_act, ArrayList<String> picAct)
    {
        this.con = con;
        this.data_subAct = subAct;
        this.data_detail = detail;
        this.data_id_act = id_act;
        this.data_picAct = picAct;

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

        switch (data_picAct.get(position).trim()){
            case "0":
                picAct.setImageResource(R.drawable.logo);
                break;
            default:
                //decode Pic
//                byte[] decodedString = Base64.decode(data_picAct.get(position), Base64.DEFAULT);
//                Log.d("decodedString", String.valueOf(decodedString));
//
//                BitmapFactory.Options opt = new BitmapFactory.Options();
//                opt.inDensity = 100;
//                opt.inTargetDensity = 100;
//                opt.outHeight = 120;
//                opt.outWidth = 120;
//                opt.inJustDecodeBounds = true;
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opt);
//                Log.d("decodedByte", String.valueOf(decodedByte));
//                picAct.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,120,120,false));
                byte[] decodedString = Base64.decode(data_picAct.get(position), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                picAct.setImageBitmap(decodedByte);
        }

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
            }
        }

        return convertView;
    }
}
