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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditClubActivity extends AppCompatActivity {
    String nameClub = null;
    String detailClub = null;
    String stdIDHead = null;
    String nameHead = null;
    String phoneHead = null;
    String nameAj = null;
    String phoneAj = null;
    String id_FHT = null;
    String id_FIS = null;
    String id_FTE = null;
    String id_CoE = null;
    String id_club = null;

    Integer role_sp = 0;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        role_sp = sp.getInt("dataRole",0);

        Bundle data = getIntent().getExtras();
        nameClub = data.getString("nameClub");
        detailClub = data.getString("detailClub");
        stdIDHead = data.getString("stdIDHead");
        nameHead = data.getString("nameHead");
        phoneHead = data.getString("phoneHead");
        nameAj = data.getString("nameAj");
        phoneAj = data.getString("phoneAj");
        id_FHT = data.getString("id_FHT");
        id_FIS = data.getString("id_FIS");
        id_FTE = data.getString("id_FTE");
        id_CoE = data.getString("id_CoE");
        id_club = data.getString("id_club");
//        Log.d("xxxx",name);

        final EditText nameClub_A = (EditText)findViewById(R.id.nameClub);
        final EditText detailClub_A = (EditText)findViewById(R.id.detailClub);
        final EditText stdIDHead_A = (EditText)findViewById(R.id.stdIDHead);
        final EditText nameHead_A = (EditText)findViewById(R.id.nameHead);
        final EditText phoneHead_A = (EditText)findViewById(R.id.phoneHead);
        final EditText nameAj_A = (EditText)findViewById(R.id.nameAj);
        final EditText phoneAj_A = (EditText)findViewById(R.id.phoneAj);
        final CheckBox id_FHT_A = (CheckBox)findViewById(R.id.id_FHT);
        final CheckBox id_FIS_A = (CheckBox)findViewById(R.id.id_FIS);
        final CheckBox id_FTE_A = (CheckBox)findViewById(R.id.id_FTE);
        final CheckBox id_CoE_A = (CheckBox)findViewById(R.id.id_CoE);
        final ImageView picClub = (ImageView) findViewById(R.id.picClub);

        if(role_sp == 5){
            nameClub_A.setEnabled(false);
            stdIDHead_A.setEnabled(false);
            nameHead_A.setEnabled(false);
        }

        nameClub_A.setText(nameClub);
        detailClub_A.setText(detailClub);
        stdIDHead_A.setText(stdIDHead);
        nameHead_A.setText(nameHead);
        phoneHead_A.setText(phoneHead);
        nameAj_A.setText(nameAj);
        phoneAj_A.setText(phoneAj);
        id_FHT_A.setChecked(convertStringToBoolean(id_FHT));
        id_FIS_A.setChecked(convertStringToBoolean(id_FIS));
        id_FTE_A.setChecked(convertStringToBoolean(id_FTE));
        id_CoE_A.setChecked(convertStringToBoolean(id_CoE));
        switch (id_club.trim()){
            case "6": picClub.setImageResource(R.drawable.c6); break;
            case "7": picClub.setImageResource(R.drawable.c7); break;
            case "8": picClub.setImageResource(R.drawable.c8); break;
            case "9": picClub.setImageResource(R.drawable.c9); break;
            case "10": picClub.setImageResource(R.drawable.c10); break;
            case "11": picClub.setImageResource(R.drawable.c11); break;
            case "12": picClub.setImageResource(R.drawable.c12); break;
            case "13": picClub.setImageResource(R.drawable.c13); break;
            case "14": picClub.setImageResource(R.drawable.c14); break;
            case "16": picClub.setImageResource(R.drawable.c16); break;
            default: picClub.setImageResource(R.drawable.logo);
        }

        Button editClub = (Button)findViewById(R.id.editClub);
        editClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameClub = nameClub_A.getText().toString();
                detailClub = detailClub_A.getText().toString();
                stdIDHead = stdIDHead_A.getText().toString();
                nameHead = nameHead_A.getText().toString();
                phoneHead = phoneHead_A.getText().toString();
                nameAj = nameAj_A.getText().toString();
                phoneAj = phoneAj_A.getText().toString();

                Boolean FHT = id_FHT_A.isChecked();
                final int id_FHT = (FHT) ? 1 : 0;
                Boolean FIS = id_FIS_A.isChecked();
                final int id_FIS = (FIS) ? 1 : 0;
                Boolean FTE = id_FTE_A.isChecked();
                final int id_FTE = (FTE) ? 1 : 0;
                Boolean CoE = id_CoE_A.isChecked();
                final int id_CoE = (CoE) ? 1 : 0;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(EditClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder2.setTitle("ท่านต้องการแก้ไขชมรมนี้ใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new edit().execute(nameClub, detailClub, stdIDHead, nameHead, phoneHead, nameAj, phoneAj,String.valueOf(id_FHT), String.valueOf(id_FIS), String.valueOf(id_FTE), String.valueOf(id_CoE),id_club);
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

    }

    public Boolean convertStringToBoolean(String s){

        if (Integer.valueOf(s) == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public class edit extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("nameClub_A",params[0])
                    .add("detailClub_A", params[1])
                    .add("stdIDHead_A",params[2])
                    .add("nameHead_A",params[3])
                    .add("phoneHead_A",params[4])
                    .add("nameAj_A",params[5])
                    .add("phoneAj_A",params[6])
                    .add("id_FHT_A",params[7])
                    .add("id_FIS_A",params[8])
                    .add("id_FTE_A",params[9])
                    .add("id_CoE_A",params[10])
                    .add("id_club_A",params[11])
                    .build();

            OkHttpClient oktest = new OkHttpClient();
            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/editClub.php").post(body).build();

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
            CallBack();
        }
    }

    @Override
    public void onBackPressed() {
        CallBack();
    }

    public void CallBack (){
        if(role_sp == 1){
            Intent intent = new Intent(getBaseContext(),ClubAllActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(getBaseContext(),ClubHeadActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
