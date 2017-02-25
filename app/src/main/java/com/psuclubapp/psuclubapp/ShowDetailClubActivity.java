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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.data;
import static com.psuclubapp.psuclubapp.R.drawable.logoapp;


public class ShowDetailClubActivity extends AppCompatActivity {

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

    public static String username_sp = null;
    public static Integer role_sp = 0;
    public static String nameStd_sp = null;
    public static String facStd_sp = null;
    public static String emailStd_sp = null;
    public static Integer checkHead_sp = 0;
    public static Integer checkAll_sp = 0;
    public static Integer checkFollow_sp = 0;
    public static Integer checkMe_sp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_club);

        //ดึงข้อมูลของ User จากหน้า log in โดยไม่ใช่ intent
        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        facStd_sp = sp.getString("dataFacStd", null);
        nameStd_sp = sp.getString("dataNameStd", null);
        emailStd_sp = sp.getString("dataEmailStd", null);
        role_sp = sp.getInt("dataRole",0);
        checkHead_sp = sp.getInt("dataCheckHead",0);
        checkAll_sp = sp.getInt("dataCheckAll",0);
        checkFollow_sp = sp.getInt("dataCheckFollow",0);
        checkMe_sp = sp.getInt("dataCheckMe",0);

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

        final TextView nameClub_A = (TextView) findViewById(R.id.nameClub);
        final TextView detailClub_A = (TextView) findViewById(R.id.detailClub);
        final TextView stdIDHead_A = (TextView) findViewById(R.id.stdIDHead);
        final TextView nameHead_A = (TextView) findViewById(R.id.nameHead);
        final TextView phoneHead_A = (TextView) findViewById(R.id.phoneHead);
        final TextView nameAj_A = (TextView) findViewById(R.id.nameAj);
        final TextView phoneAj_A = (TextView) findViewById(R.id.phoneAj);
        final CheckBox id_FHT_A = (CheckBox) findViewById(R.id.id_FHT);
        final CheckBox id_FIS_A = (CheckBox) findViewById(R.id.id_FIS);
        final CheckBox id_FTE_A = (CheckBox) findViewById(R.id.id_FTE);
        final CheckBox id_CoE_A = (CheckBox) findViewById(R.id.id_CoE);
        final ImageView picClub = (ImageView) findViewById(R.id.picClub);

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


        Button regisClub = (Button) findViewById(R.id.regisClub);
        Button exitClub = (Button) findViewById(R.id.exitClub);
        Button editClub = (Button) findViewById(R.id.editClub);
        Button deleteClub = (Button) findViewById(R.id.deleteClub);
        Button followClub = (Button) findViewById(R.id.followClub);
        Button unFollowClub = (Button) findViewById(R.id.unFollowClub);
        Button callBack = (Button) findViewById(R.id.callBack);

        if(checkHead_sp == 1){
            regisClub.setVisibility(View.GONE);
            exitClub.setVisibility(View.GONE);
            deleteClub.setVisibility(View.GONE);
            followClub.setVisibility(View.GONE);
            unFollowClub.setVisibility(View.GONE);
        }
        else if(checkAll_sp == 1){
            if(role_sp == 1){
                regisClub.setVisibility(View.GONE);
                exitClub.setVisibility(View.GONE);
                followClub.setVisibility(View.GONE);
                unFollowClub.setVisibility(View.GONE);
            }
            else {
                exitClub.setVisibility(View.GONE);
                editClub.setVisibility(View.GONE);
                deleteClub.setVisibility(View.GONE);
                unFollowClub.setVisibility(View.GONE);
            }
        }
        else if(checkFollow_sp == 1){
            exitClub.setVisibility(View.GONE);
            editClub.setVisibility(View.GONE);
            deleteClub.setVisibility(View.GONE);
            followClub.setVisibility(View.GONE);
        }
        else if(checkMe_sp == 1){
            regisClub.setVisibility(View.GONE);
            editClub.setVisibility(View.GONE);
            deleteClub.setVisibility(View.GONE);
            followClub.setVisibility(View.GONE);
            unFollowClub.setVisibility(View.GONE);
        }

        regisClub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder1.setTitle("ท่านต้องการลงทะเบียนชมรมนี้ใช่หรือไม่ ?");
                builder1.setCancelable(true);
                builder1.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new regisClub().execute(username_sp,facStd_sp,id_club);
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

        editClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(ShowDetailClubActivity.this, EditClubActivity.class);
                data.putExtra("nameClub", nameClub);
                data.putExtra("detailClub", detailClub);
                data.putExtra("stdIDHead", stdIDHead);
                data.putExtra("nameHead", nameHead);
                data.putExtra("phoneHead", phoneHead);
                data.putExtra("nameAj", nameAj);
                data.putExtra("phoneAj", phoneAj);
                data.putExtra("id_FHT", id_FHT);
                data.putExtra("id_FIS", id_FIS);
                data.putExtra("id_FTE", id_FTE);
                data.putExtra("id_CoE", id_CoE);
                data.putExtra("id_club", id_club);
                startActivity(data);
                finish();
            }
        });

        deleteClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder1.setTitle("ท่านต้องการลบชมรมนี้ใช่หรือไม่ ?");
                builder1.setCancelable(true);
                builder1.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new delete().execute(id_club);
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

        followClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder1.setTitle("ท่านต้องการติดตามชมรมนี้ใช่หรือไม่ ?");
                builder1.setCancelable(true);
                builder1.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new follow().execute(id_club,username_sp);
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

        unFollowClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder1.setTitle("ท่านต้องการเลิกติดตามชมรมนี้ใช่หรือไม่ ?");
                builder1.setCancelable(true);
                builder1.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new unFollow().execute(id_club,username_sp);
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

        exitClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder1.setTitle("ท่านต้องการออกจากชมรมนี้ใช่หรือไม่ ?");
                builder1.setCancelable(true);
                builder1.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new exit().execute(id_club,username_sp);
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
                if(checkAll_sp == 1){
                    BackToClubAll();
                }
                else if (checkMe_sp == 1){
                    BackToClubMe();
                }
                else if(checkFollow_sp == 1){
                    BackToClubFollow();
                }
                else if(checkHead_sp == 1){
                    BackToClubHead();
                }
            }
        });


    }

    public Integer convertStringToInteger(String s) {
        return Integer.valueOf(s);

    }

    public Boolean convertStringToBoolean(String s) {

        if (Integer.valueOf(s) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public class delete extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("id_club_A", params[0])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/deleteClub.php").post(body).build();

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
            BackToClubAll();
        }

    }


    public class regisClub extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("stdID_A",params[0])
                    .add("facStd_A", params[1])
                    .add("id_club_A",params[2])
                    .build();

            OkHttpClient okh = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/regisClub.php")
                    .post(body)
                    .build();

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

        protected void onPostExecute(String s) {
            Log.d("Test_regisClub", s);

            switch (s.trim()){
                case "1":
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder1.setTitle("การลงทะเบียนสำเร็จ");
                    builder1.setMessage("ท่านเป็นสมาชิกของชมรมนี้เรียบร้อยแล้ว");
                    builder1.setCancelable(true);
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                    break;
                case "2":
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder2.setTitle("การลงทะเบียนล้มเหลว");
                    builder2.setMessage("ท่านไม่ได้อยู่ในคณะที่สามารถลงทะเบียนชมรมนี้ได้");
                    builder2.setCancelable(true);
                    AlertDialog alert2 = builder2.create();
                    alert2.show();
                    break;
                case "3":
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder3.setTitle("การลงทะเบียนล้มเหลว");
                    builder3.setMessage("ท่านลงทะเบียนชมรมครบ 3 ชมรมแล้ว ไม่สามารถลงทะเบียนเพิ่มได้");
                    builder3.setCancelable(true);
                    AlertDialog alert3 = builder3.create();
                    alert3.show();
                    break;
                case "4":
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder4.setTitle("การลงทะเบียนล้มเหลว");
                    builder4.setMessage("ท่านเป็นสมาชิกของชมรมนี้เรียบร้อยแล้ว ไม่สามารถลงทะเบียนซ้ำได้");
                    builder4.setCancelable(true);
                    AlertDialog alert4 = builder4.create();
                    alert4.show();
                    break;
                case "0":
                    AlertDialog.Builder builder0 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder0.setTitle("การลงทะเบียนล้มเหลว");
                    builder0.setMessage("กรุณาลงทะเบียนเข้าใช้งานแอพพลิเคชันก่อนลงทะเบียนชมรม");
                    builder0.setCancelable(true);
                    builder0.setNegativeButton("ลงทะเบียนเข้าใช้งาน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentID = new Intent(getBaseContext(), RegisAppActivity.class);
                            startActivity(intentID);
                            finish();
                        }
                    });
                    builder0.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alert0 = builder0.create();
                    alert0.show();
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder.setTitle("การลงทะเบียนล้มเหลว");
                    builder.setMessage("ไม่สามารถลงทะเบียนได้ กรุณาลองใหม่อีกครั้ง");
                    builder.setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.show();
            }

        }

    }

    public class exit extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("id_club_A", params[0])
                    .add("stdID_A", params[1])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/exitClub.php").post(body).build();

            String resultData = null;

            try {
                Response res = oktest.newCall(request).execute();
                resultData = res.body().string();
                return resultData;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Test_exitClub", s);

            switch (s.trim()) {
                case "1":
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder1.setTitle("ออกจากชมรมเรียบร้อยเเล้ว");
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("ทราบ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BackToClubMe();
                        }
                    });
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder.setTitle("การออกจากชมรมชมรมล้มเหลว");
                    builder.setMessage("ไม่สามารถออกจากชมรมได้ กรุณาลองใหม่อีกครั้ง");
                    builder.setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.show();
            }


        }

    }

    public class follow extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("id_club_A", params[0])
                    .add("stdID_A", params[1])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/followClub.php").post(body).build();

            String resultData = null;

            try {
                Response res = oktest.newCall(request).execute();
                resultData = res.body().string();
                return resultData;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Test_followClub", s);

            switch (s.trim()) {
                case "1":
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder1.setTitle("การติดตามสำเร็จ");
                    builder1.setMessage("ท่านได้ติดตามชมรมนี้เรียบร้อยแล้ว");
                    builder1.setCancelable(true);
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder.setTitle("การติดตามล้มเหลว");
                    builder.setMessage("ท่านได้ติดตามชมรมนี้เรียบร้อยแล้ว ไม่สามารถติดตามซ้ำได้");
                    builder.setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.show();
            }

        }

    }

    public class unFollow extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("id_club_A", params[0])
                    .add("stdID_A", params[1])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/unFollowClub.php").post(body).build();

            String resultData = null;

            try {
                Response res = oktest.newCall(request).execute();
                resultData = res.body().string();
                return resultData;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Test_unFollowClub", s);

            switch (s.trim()) {
                case "1":
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder1.setTitle("ยกเลิกการติดตามเรียบร้อยเเล้ว");
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("ทราบ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BackToClubFollow();
                        }
                    });
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailClubActivity.this);
                    builder.setTitle("การเลิกติดตามชมรมล้มเหลว");
                    builder.setMessage("ไม่สามารถเลิกติดตามได้ กรุณาลองใหม่อีกครั้ง");
                    builder.setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.show();
            }

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

    public void BackToClubAll() {
        Intent intent = new Intent(getBaseContext(), ClubAllActivity.class);
        startActivity(intent);
        finish();
    }

    public void BackToClubFollow() {
        Intent intent = new Intent(getBaseContext(), ClubFollowActivity.class);
        startActivity(intent);
        finish();
    }

    public void BackToClubMe() {
        Intent intent = new Intent(getBaseContext(), ClubMeActivity.class);
        startActivity(intent);
        finish();
    }

    public void BackToClubHead() {
        Intent intent = new Intent(getBaseContext(), ClubHeadActivity.class);
        startActivity(intent);
        finish();
    }

}





