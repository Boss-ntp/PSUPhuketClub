package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActActivity extends AppCompatActivity {

    String id_act = null;
    String subAct = null;
    String detailAct = null;
    String startD = null;
    String endD = null;
    String startT = null;
    String endT = null;
    String location = null;

    public Integer role_sp = 0;
    public String username_sp = null;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_act);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        role_sp = sp.getInt("dataRole",0);
        username_sp = sp.getString("dataUsername", null);

        Bundle data = getIntent().getExtras();
        id_act = data.getString("id_act");
        subAct = data.getString("subAct");
        detailAct = data.getString("detailAct");
        startD = data.getString("startD");
        endD = data.getString("endD");
        startT = data.getString("startT");
        endT = data.getString("endT");
        location = data.getString("location");

        final EditText subAct_A = (EditText) findViewById(R.id.subAct);
        final EditText detailAct_A = (EditText) findViewById(R.id.detailAct);
        final EditText startD_A = (EditText) findViewById(R.id.startD);
        final EditText endD_A = (EditText) findViewById(R.id.endD);
        final EditText startT_A = (EditText) findViewById(R.id.startT);
        final EditText endT_A = (EditText) findViewById(R.id.endT);
        final EditText location_A = (EditText) findViewById(R.id.location);
        final ImageView picAct = (ImageView) findViewById(R.id.picAct);

        subAct_A.setText(subAct);
        detailAct_A.setText(detailAct);
        startD_A.setText(startD);
        endD_A.setText(endD);
        startT_A.setText(startT);
        endT_A.setText(endT);
        location_A.setText(location);
        switch (id_act.trim()){
            case "15": picAct.setImageResource(R.drawable.a15); break;
            case "16": picAct.setImageResource(R.drawable.a16); break;
            case "29": picAct.setImageResource(R.drawable.a29); break;
            case "30": picAct.setImageResource(R.drawable.a30); break;
            case "31": picAct.setImageResource(R.drawable.a31); break;
            case "32": picAct.setImageResource(R.drawable.a32); break;
            case "33": picAct.setImageResource(R.drawable.a33); break;
            default: picAct.setImageResource(R.drawable.logo);
        }

        Button editAct = (Button)findViewById(R.id.editAct);
        editAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subAct = subAct_A.getText().toString();
                detailAct = detailAct_A.getText().toString();
                startD = startD_A.getText().toString();
                endD = endD_A.getText().toString();
                startT = startT_A.getText().toString();
                endT = endT_A.getText().toString();
                location = location_A.getText().toString();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(EditActActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder2.setTitle("ท่านต้องการแก้ไขกิจกรรมนี้ใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new edit().execute(subAct, detailAct, startD, endD, startT, endT,location,username_sp);
                    }
                });
                builder2.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert11 = builder2.create();
                alert11.show();
            }
        });

        Button backToList = (Button)findViewById(R.id.backToList);
        backToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActHeadActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class edit extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("subAct_A",params[0])
                    .add("detailAct_A", params[1])
                    .add("startD_A",params[2])
                    .add("endD_A",params[3])
                    .add("startT_A",params[4])
                    .add("endT_A",params[5])
                    .add("location_A",params[6])
                    .add("stdID_A",params[7])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/editAct.php").post(body).build();

            try {
                Response res = oktest.newCall(request).execute();
                Log.d("AAA",res.body().string());
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
}
