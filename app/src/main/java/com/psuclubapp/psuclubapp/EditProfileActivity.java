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
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity {
    String stdID = null;
    String nameStd = null;
    String facStd = null;
    String phoneStd = null;
    String emailStd = null;
    String fbStd = null;

    public static String username_sp = null;
    public static Integer role_sp = 0;
    public static String nameStd_sp = null;
    public static String facStd_sp = null;
    public static String emailStd_sp = null;
    public static String phoneStd_sp = null;
    public static String fbStd_sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //ดึงข้อมูลของ User จากหน้า log in โดยไม่ใช่ intent
        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        facStd_sp = sp.getString("dataFacStd", null);
        nameStd_sp = sp.getString("dataNameStd", null);
        emailStd_sp = sp.getString("dataEmailStd", null);
        phoneStd_sp = sp.getString("dataPhoneStd", null);
        fbStd_sp = sp.getString("dataFbStd", null);
        role_sp = sp.getInt("dataRole",0);

        stdID = username_sp;
        nameStd = facStd_sp;
        facStd = nameStd_sp;
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

        Button editProfile = (Button) findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stdID = stdID_A.getText().toString();
                nameStd = nameStd_A.getText().toString();
                facStd = facStd_A.getText().toString();
                phoneStd = phoneStd_A.getText().toString();
                emailStd = emailStd_A.getText().toString();
                fbStd = fbStd_A.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder.setTitle("ท่านต้องการแก้ไขข้อมูลส่วนตัวใช่หรือไม่ ?");
                builder.setCancelable(true);
                builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new edit().execute(stdID, phoneStd, emailStd, fbStd);
                    }
                });
                builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });

    }

    public class edit extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("stdID_A",params[0])
                    .add("phoneStd_A",params[1])
                    .add("emailStd_A",params[2])
                    .add("fbStd_A",params[3])
                    .build();

            OkHttpClient oktest = new OkHttpClient();
            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/editProfile.php").post(body).build();

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
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void BackToMain() {
        Intent intent = new Intent(getBaseContext(), Main2Activity.class);
        startActivity(intent);
        finish();
    }
}
