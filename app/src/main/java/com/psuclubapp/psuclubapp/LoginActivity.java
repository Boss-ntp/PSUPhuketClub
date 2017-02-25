package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.psuclubapp.psuclubapp.ShowDetailClubActivity.facStd_sp;
import static com.psuclubapp.psuclubapp.ShowDetailClubActivity.nameStd_sp;

public class LoginActivity extends AppCompatActivity {
    public static String username_sp = null;
    public static String password_sp = null;
    public static Integer checkLogin_sp = 0;
    public static Integer role_sp = 0;
    public static String nameStd_sp = null;
    public static String emailStd_sp = null;
    public static String facStd_sp = null;
    public static String phoneStd_sp = null;
    public static String fbStd_sp = null;
    public static Integer head_sp = 0;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        checkLogin_sp = sp.getInt("dataCheckLogin",0);
        //Log.d("dataCheckLogin", String.valueOf(checkLogin_sp));

        if (checkLogin_sp == 1 & role_sp == 1){
            Intent intentID = new Intent(getBaseContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", username_sp);
            intentID.putExtras(bundle);
            startActivity(intentID);
            finish();
        }
        else if(checkLogin_sp == 1 & role_sp == 5){
            Intent intentID = new Intent(getBaseContext(), Main2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", username_sp);
            intentID.putExtras(bundle);
            startActivity(intentID);
            finish();
        }

        Button logIn = (Button) findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //เข้าเมื่อกดปุ่ม Log in
                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);
                username_sp = username.getText().toString();
                password_sp = password.getText().toString();

                Log.d("username",username_sp);
                Log.d("password",password_sp);

                new checkLogIn().execute(username_sp, password_sp); //เรียกใช้คลาสที่ร้องขอข้อมูล
            }

        });
    }

    public class checkLogIn extends AsyncTask<String,Void,JSONArray>
    {
        private ProgressDialog progressDialog;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(LoginActivity.this);
            this.dialog.setTitle("กรุณารอสักครู่");
            this.dialog.setMessage("กำลังตรวจสอบการเข้าสู่ระบบ...");
            this.dialog.setCancelable(true);
            this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
            {
                @Override
                public void onCancel(DialogInterface dialog)
                {
                    // cancel AsyncTask
                    cancel(false);
                }
            });

            this.dialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("username_A", params[0])
                    .add("password_A", params[1])
                    .build();

            OkHttpClient okh = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request requested = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/checkLogin.php")
                    .post(body)
                    .build();

            try {
                Response response = okh.newCall(requested).execute();
                JSONArray jsonArray = new JSONArray(response.body().string());
                Log.d("AAA", response.body().string());
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
                            String role = j.getString(0);
                            role_sp = Integer.valueOf(role);
                            break;
                        case 1:
                            String username = j.getString(1);
                            username_sp = username;
                            break;
                        case 2:
                            String nameStd = j.getString(2);
                            nameStd_sp = nameStd;
                            break;
                        case 3:
                            String facStd = j.getString(3);
                            facStd_sp = facStd;
                            break;
                        case 4:
                            String emailStd = j.getString(4);
                            emailStd_sp = emailStd;
                            break;
                        case 5:
                            String phoneStd = j.getString(5);
                            phoneStd_sp = phoneStd;
                            break;
                        case 6:
                            String fbStd = j.getString(6);
                            fbStd_sp = fbStd;
                            break;
                        case 7:
                            String head = j.getString(7);
                            head_sp = Integer.valueOf(head);
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
            editor = sp.edit();
            editor.putString("dataUsername", username_sp);
            editor.putInt("dataRole", role_sp);
            editor.putString("dataNameStd", nameStd_sp);
            editor.putString("dataFacStd", facStd_sp);
            editor.putString("dataEmailStd", emailStd_sp);
            editor.putString("dataPhoneStd", phoneStd_sp);
            editor.putString("dataFbStd", fbStd_sp);
            editor.putInt("dataHead", head_sp);
            editor.commit();

            Log.d("dataUsername_admin",username_sp);
//            Log.d("dataRole",String.valueOf(role_sp));
//            Log.d("dataNameStd",nameStd_sp);
//            Log.d("dataFacStd",facStd_sp);
//            Log.d("dataEmailStd",emailStd_sp);
//            Log.d("dataPhoneStd",phoneStd_sp);
//            Log.d("dataFbStd",fbStd_sp);
            Log.d("dataHead", String.valueOf(head_sp));

            if (role_sp == 1) {
                this.dialog.cancel();
                checkLogin_sp = 1;

                sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.putString("dataUsername", username_sp);
                editor.putInt("dataCheckLogin", checkLogin_sp);
                editor.putString("dataNameStd", null);
                editor.putString("dataFacStd", null);
                editor.putString("dataEmailStd", null);
                editor.putString("dataPhoneStd", null);
                editor.putString("dataFbStd", null);
                editor.commit();

                Intent intentID = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", username_sp);
                intentID.putExtras(bundle);
                startActivity(intentID);
                finish();

            } else if (role_sp == 5) {
                this.dialog.cancel();
                checkLogin_sp = 1;

                sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.putInt("dataCheckLogin", checkLogin_sp);
                editor.commit();

                Intent intentID = new Intent(getBaseContext(), Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", username_sp);
                intentID.putExtras(bundle);
                startActivity(intentID);
                finish();

            } else if (role_sp == 0) {
                this.dialog.cancel();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this); //pop up เด้งเมื่อ login failed
                builder1.setTitle("การเข้าสู่ระบบล้มเหลว");
                builder1.setMessage("ไม่สามารถเข้าสู่ระบบได้ กรุณาลองใหม่อีกครั้ง");
                builder1.setCancelable(true);
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

        }
    }
}