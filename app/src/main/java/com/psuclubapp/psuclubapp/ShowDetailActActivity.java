package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowDetailActActivity extends AppCompatActivity {

    String id_act = null;
    String subAct = null;
    String detailAct = null;
    String startD = null;
    String endD = null;
    String startT = null;
    String endT = null;
    String location = null;
    String followJoin = null;
    String picAct = null;
    String nameClub = null;

    public static String username_sp = null;
    public static Integer role_sp = 0;
    public static Integer checkActHead_sp;
    public static Integer checkActFollow_sp;
    public static Integer checkActAll_sp;
    public static Integer checkActMe_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_act);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        role_sp = sp.getInt("dataRole",0);
        checkActHead_sp = sp.getInt("dataCheckActHead",0);
        checkActFollow_sp = sp.getInt("dataCheckActFollow",0);
        checkActAll_sp = sp.getInt("dataCheckActAll",0);
        checkActMe_sp = sp.getInt("dataCheckActMe",0);

        Bundle data = getIntent().getExtras();
        id_act = data.getString("id_act");
        subAct = data.getString("subAct");
        detailAct = data.getString("detailAct");
        startD = data.getString("startD");
        endD = data.getString("endD");
        startT = data.getString("startT");
        endT = data.getString("endT");
        location = data.getString("location");
        followJoin = data.getString("followJoin");
        picAct = data.getString("picAct");
        nameClub = data.getString("nameClub");

        final TextView subAct_A = (TextView) findViewById(R.id.subAct);
        final TextView detailAct_A = (TextView) findViewById(R.id.detailAct);
        final TextView startD_A = (TextView) findViewById(R.id.startD);
        final TextView endD_A = (TextView) findViewById(R.id.endD);
        final TextView startT_A = (TextView) findViewById(R.id.startT);
        final TextView endT_A = (TextView) findViewById(R.id.endT);
        final TextView location_A = (TextView) findViewById(R.id.location);
        final TextView nameClub_A = (TextView) findViewById(R.id.nameClub);
        final Switch followJoin_A = (Switch) findViewById(R.id.followJoin);
        final ImageView picAct_A = (ImageView) findViewById(R.id.picAct);

        subAct_A.setText(subAct);
        detailAct_A.setText(detailAct);
        startD_A.setText(startD);
        endD_A.setText(endD);
        startT_A.setText(startT);
        endT_A.setText(endT);
        location_A.setText(location);
        followJoin_A.setChecked(convertStringToBoolean(followJoin));
        nameClub_A.setText(nameClub);

        switch (picAct.trim()){
            case "0":
                picAct_A.setImageResource(R.drawable.logo);
                break;
            default:
                //decode Pic
                byte[] decodedString = Base64.decode(picAct, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                picAct_A.setImageBitmap(decodedByte);
        }

        switch (id_act.trim()) {
            case "15":
                picAct_A.setImageResource(R.drawable.a15);
                break;
            case "16":
                picAct_A.setImageResource(R.drawable.a16);
                break;
            case "29":
                picAct_A.setImageResource(R.drawable.a29);
                break;
            case "30":
                picAct_A.setImageResource(R.drawable.a30);
                break;
            case "31":
                picAct_A.setImageResource(R.drawable.a31);
                break;
            case "32":
                picAct_A.setImageResource(R.drawable.a32);
                break;
            case "33":
                picAct_A.setImageResource(R.drawable.a33);
                break;
        }

        Button deleteAct = (Button) findViewById(R.id.deleteAct);
        Button editAct = (Button) findViewById(R.id.editAct);
        Button callBack = (Button) findViewById(R.id.callBack);
        Log.d("checkActHead_sp", String.valueOf(checkActHead_sp));
        Log.d("checkActFollow_sp", String.valueOf(checkActFollow_sp));
        Log.d("checkActAll_sp", String.valueOf(checkActAll_sp));
        Log.d("checkActMe_sp", String.valueOf(checkActMe_sp));


        if(checkActHead_sp == 1){

        }
        else{
            deleteAct.setVisibility(View.GONE);
            editAct.setVisibility(View.GONE);
        }

        editAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(ShowDetailActActivity.this, EditActActivity.class);
                data.putExtra("id_act", id_act);
                data.putExtra("subAct", subAct);
                data.putExtra("detailAct", detailAct);
                data.putExtra("startD", startD);
                data.putExtra("endD", endD);
                data.putExtra("startT", startT);
                data.putExtra("endT", endT);
                data.putExtra("location", location);
                data.putExtra("followJoin", followJoin);
                data.putExtra("picAct", picAct);
                startActivity(data);
                finish();
            }
        });

        deleteAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailActActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder1.setTitle("ท่านต้องการลบกิจกรรมนี้ใช่หรือไม่ ?");
                builder1.setCancelable(true);
                builder1.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new delete().execute(id_act);
                    }
                });
                builder1.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        });

        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(checkActHead_sp == 1){
                    Intent intent = new Intent(getBaseContext(), ActHeadActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(checkActAll_sp == 1){
                    Intent intent = new Intent(getBaseContext(), ActAllActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(checkActMe_sp == 1){
                    Intent intent = new Intent(getBaseContext(), ActMeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(getBaseContext(), ActFollowActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public class delete extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("id_act_A", params[0])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/deleteAct.php").post(body).build();

            try {
                Response res = oktest.newCall(request).execute();
                Log.d("AAA", res.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(getBaseContext(), ActHeadActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public Boolean convertStringToBoolean(String s) {

        if (Integer.valueOf(s) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
