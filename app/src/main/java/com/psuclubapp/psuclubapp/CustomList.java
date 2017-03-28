package com.psuclubapp.psuclubapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
public class CustomList extends BaseAdapter {

    private Context con;
    private ArrayList<String> data_nameClub;
    private ArrayList<String> data_detail;
    private ArrayList<String> data_id_club;
    private ArrayList<String> data_picClub;

    public CustomList(Context con, ArrayList<String> name, ArrayList<String> detail, ArrayList<String> id_club, ArrayList<String> picClub)
    {
        this.con = con;
        this.data_nameClub = name;
        this.data_detail = detail;
        this.data_id_club = id_club;
        this.data_picClub = picClub;
    }

    @Override
    public int getCount() { //มีกี่ตัว
        return data_nameClub.size();
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
            convertView  =  lay.inflate(R.layout.custom_list,null);
            TextView textView = (TextView)convertView.findViewById(R.id.nameClub);
            TextView textView2 = (TextView)convertView.findViewById(R.id.detailClub);
            ImageView picClub = (ImageView)convertView.findViewById(R.id.picClub);
            textView.setText(data_nameClub.get(position));
            textView2.setText(data_detail.get(position));

        switch (data_picClub.get(position).trim()){
            case "0":
                picClub.setImageResource(R.drawable.logo);
                break;
            default:
                //decode Pic
                byte[] decodedString = Base64.decode(data_picClub.get(position), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                picClub.setImageBitmap(decodedByte);
        }

//        String array[] = new String[data_id_club.size()];
//        for(int j =0;j<data_id_club.size();j++){
//            array[j] = data_id_club.get(position);
//            switch (array[j]){
//                case "6": picClub.setImageResource(R.drawable.c6); break;
//                case "7": picClub.setImageResource(R.drawable.c7); break;
//                case "8": picClub.setImageResource(R.drawable.c8); break;
//                case "9": picClub.setImageResource(R.drawable.c9); break;
//                case "10": picClub.setImageResource(R.drawable.c10); break;
//                case "11": picClub.setImageResource(R.drawable.c11); break;
//                case "12": picClub.setImageResource(R.drawable.c12); break;
//                case "13": picClub.setImageResource(R.drawable.c13); break;
//                case "14": picClub.setImageResource(R.drawable.c14); break;
//                case "16": picClub.setImageResource(R.drawable.c16); break;
//                default: picClub.setImageResource(R.drawable.logo);
//            }
//        }




        return convertView;
    }
}
