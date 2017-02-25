package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisAppActivity extends AppCompatActivity {
    public  String stdID = null;
    public  String nameStd = null;
    public  String facStd = null;
    public  String phoneStd = null;
    public  String emailStd = null;
    public  String fbStd = null;

    public static String username_sp = null;
    public static String nameStd_sp = null;
    public static String facStd_sp = null;
    public static String emailStd_sp = null;
    public static String phoneStd_sp = null;
    public static String fbStd_sp = null;
    public static int role_sp;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_app);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        facStd_sp = sp.getString("dataFacStd", null);
        nameStd_sp = sp.getString("dataNameStd", null);
        emailStd_sp = sp.getString("dataEmailStd", null);
        phoneStd_sp = sp.getString("dataPhoneStd", null);
        fbStd_sp = sp.getString("dataFbStd", null);
        role_sp = sp.getInt("dataRole",0);

        final EditText stdID_A = (EditText)findViewById(R.id.stdID);
        final EditText nameStd_A = (EditText)findViewById(R.id.nameStd);
        final EditText facStd_A = (EditText)findViewById(R.id.facStd);
        final EditText phoneStd_A = (EditText)findViewById(R.id.phoneStd);
        final EditText emailStd_A = (EditText)findViewById(R.id.emailStd);
        final EditText fbStd_A = (EditText)findViewById(R.id.fbStd);

        stdID_A.setText(username_sp);
        nameStd_A.setText(nameStd_sp);
        facStd_A.setText(facStd_sp);
        emailStd_A.setText(emailStd_sp);
        phoneStd_A.setText(phoneStd_sp);
        fbStd_A.setText(fbStd_sp);

        new update().execute(username_sp);

        Button regis_A = (Button)findViewById(R.id.regisApp);
        regis_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdID = stdID_A.getText().toString();
                String nameStd = nameStd_A.getText().toString();
                String facStd = facStd_A.getText().toString();
                String phoneStd = phoneStd_A.getText().toString();
                String emailStd = emailStd_A.getText().toString();
                String fbStd = fbStd_A.getText().toString();

                Log.d("ID",stdID);
                new add().execute(stdID,nameStd,facStd,phoneStd,emailStd,fbStd);
            }
        });

        Button backToMain = (Button)findViewById(R.id.backToMain);
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

    public class add extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("stdID_A",params[0])
                    .add("nameStd_A", params[1])
                    .add("facStd_A",params[2])
                    .add("phoneStd_A",params[3])
                    .add("emailStd_A",params[4])
                    .add("fbStd_A",params[5])
                    .build();

            OkHttpClient okh = new OkHttpClient();
            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/regisApp.php").post(body).build();
            String resultData = null;

            try {
                Response res = okh.newCall(request).execute();
                resultData = res.body().string();
                return resultData;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            Log.d("DDD", s);

            switch (s.trim()){
                case "1":
                    Log.d("DDD", "Register success");

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisAppActivity.this);
                    builder1.setTitle("การลงทะเบียนสำเร็จ");
                    builder1.setMessage("ท่านสามารถลงทะเบียนชมรมได้แล้วตอนนี้");
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("ทราบ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BackToMain2();
                        }
                    });
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                    break;

                case "2":
                    Log.d("DDD", "Register success");

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(RegisAppActivity.this); //pop up เด้งเมื่อ Regis pass
                    builder2.setTitle("การลงทะเบียนล้มเหลว");
                    builder2.setMessage("ท่านได้ลงทะเบียนไว้แล้ว ไม่สามารถลงทะเบียนซ้ำได้");
                    builder2.setCancelable(true);
                    builder2.setNegativeButton("ทราบ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BackToMain2();
                        }
                    });
                    AlertDialog alert2 = builder2.create();
                    alert2.show();
                    break;

                default:
                    Log.d("DDD", "Register no success");

                    AlertDialog.Builder builder0 = new AlertDialog.Builder(RegisAppActivity.this); //pop up เด้งเมื่อ Regis failed
                    builder0.setTitle("การลงทะเบียนล้มเหลว");
                    builder0.setMessage("ไม่สามารถลงทะเบียนได้ กรุณาลองใหม่อีกครั้ง");
                    builder0.setCancelable(true);
                    AlertDialog alert0 = builder0.create();
                    alert0.show();
                    break;
            }

        }

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
