package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    public  String stdID = null;
    public  String nameStd = null;
    public  String facStd = null;
    public  String phoneStd = null;
    public  String emailStd = null;
    public  String fbStd = null;

    public static String username_sp = null;
    public static Integer role_sp = 0;
    public static String nameStd_sp = null;
    public static String facStd_sp = null;
    public static String emailStd_sp = null;
    public static String phoneStd_sp = null;
    public static String fbStd_sp = null;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //ดึงข้อมูลของ User จากหน้า log in โดยไม่ใช่ intent
        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        facStd_sp = sp.getString("dataFacStd", null);
        nameStd_sp = sp.getString("dataNameStd", null);
        emailStd_sp = sp.getString("dataEmailStd", null);
        phoneStd_sp = sp.getString("dataPhoneStd", null);
        fbStd_sp = sp.getString("dataFbStd", null);
        role_sp = sp.getInt("dataRole",0);

        Log.d("0dataUsername",username_sp);
        Log.d("0dataRole",String.valueOf(role_sp));
        Log.d("0dataNameStd",nameStd_sp);
        Log.d("0dataFacStd",facStd_sp);
        Log.d("0dataEmailStd",emailStd_sp);
        Log.d("0dataPhoneStd",phoneStd_sp);
        Log.d("0dataFbStd",fbStd_sp);

        stdID = username_sp;
        nameStd = nameStd_sp;
        facStd = facStd_sp;
        emailStd = emailStd_sp;
        phoneStd = phoneStd_sp;
        fbStd = fbStd_sp;
        new update().execute(stdID);

        final TextView stdID_A = (TextView) findViewById(R.id.stdID);
        final TextView nameStd_A = (TextView) findViewById(R.id.nameStd);
        final TextView facStd_A = (TextView) findViewById(R.id.facStd);
        final TextView phoneStd_A = (TextView) findViewById(R.id.phoneStd);
        final TextView emailStd_A = (TextView) findViewById(R.id.emailStd);
        final TextView fbStd_A = (TextView) findViewById(R.id.fbStd);

        stdID_A.setText(stdID);
        nameStd_A.setText(nameStd);
        facStd_A.setText(facStd);
        phoneStd_A.setText(phoneStd);
        emailStd_A.setText(emailStd);
        fbStd_A.setText(fbStd);

        Button editProfile = (Button) findViewById(R.id.editProfile);
        Button backToMain = (Button) findViewById(R.id.backToMain);

        if(role_sp == 1){ //ถ้าเป็นแอดมิน จะไม่สามารถเเก้ไขข้อมูลส่วนตัวของตัวเองได้ เพราะไม่มีการเก็บข้อมูลส่วนตัว
            editProfile.setVisibility(View.GONE);
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(data);
                finish();
            }
        });
        backToMain.setVisibility(View.GONE);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(role_sp == 1){
                    BackToMain();
                    finish();
                }
                else {
                    BackToMain2();
                    finish();
                }
            }
        });

    }

    public class update extends AsyncTask<String,Void,JSONArray>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            RequestBody body = new FormBody.Builder()
                    .add("stdID_A",params[0])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/updateProfile.php").post(body).build();

            try {
                Response response = oktest.newCall(request).execute();
                JSONArray jsonArray = new JSONArray(response.body().string());
                Log.d("update", response.body().string());
                return jsonArray;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray j) {
            for(int i = 0; i<j.length();i++){
                try {
                    switch (i){
                        case 0:
                            String phone = j.getString(0);
                            phoneStd_sp = phone;
                            break;
                        case 1:
                            String email = j.getString(1);
                            emailStd_sp = email;
                            break;
                        case 2:
                            String fb = j.getString(2);
                            fbStd_sp = fb;
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
            editor = sp.edit();;
            editor.putString("dataEmailStd", emailStd_sp);
            editor.putString("dataPhoneStd", phoneStd_sp);
            editor.putString("dataFbStd", fbStd_sp);
            editor.commit();

            stdID = username_sp;
            nameStd = nameStd_sp;
            facStd = facStd_sp;
            emailStd = emailStd_sp;
            phoneStd = phoneStd_sp;
            fbStd = fbStd_sp;

            final TextView stdID_A = (TextView) findViewById(R.id.stdID);
            final TextView nameStd_A = (TextView) findViewById(R.id.nameStd);
            final TextView facStd_A = (TextView) findViewById(R.id.facStd);
            final TextView phoneStd_A = (TextView) findViewById(R.id.phoneStd);
            final TextView emailStd_A = (TextView) findViewById(R.id.emailStd);
            final TextView fbStd_A = (TextView) findViewById(R.id.fbStd);

            stdID_A.setText(stdID);
            nameStd_A.setText(nameStd);
            facStd_A.setText(facStd);
            phoneStd_A.setText(phoneStd);
            emailStd_A.setText(emailStd);
            fbStd_A.setText(fbStd);

        }
    }

    public void BackToMain2() {
        Intent intent = new Intent(getBaseContext(), Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void BackToMain() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
