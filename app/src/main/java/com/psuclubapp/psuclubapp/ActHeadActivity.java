package com.psuclubapp.psuclubapp;

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
import android.widget.ImageView;
import android.widget.ListView;

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

public class ActHeadActivity extends AppCompatActivity {

    public ArrayList<String> id_act_A = new ArrayList<>();
    public ArrayList<String> subAct_A = new ArrayList<>();
    public ArrayList<String> detailAct_A = new ArrayList<>();
    public ArrayList<String> startT_A = new ArrayList<>();
    public ArrayList<String> endT_A = new ArrayList<>();
    public ArrayList<String> startD_A = new ArrayList<>();
    public ArrayList<String> endD_A = new ArrayList<>();
    public ArrayList<String> location_A = new ArrayList<>();
    public ArrayList<String> followJoin_A = new ArrayList<>();
    public ArrayList<String> picAct_A = new ArrayList<>();
    public ArrayList<String> nameClub_A = new ArrayList<>();
    public ListView listActHead;

    public String username_sp = null;
    public Integer role_sp = 0;
    public Integer checkActHead_sp = 1;
    public Integer checkActFollow_sp = 0;
    public Integer checkActAll_sp = 0;
    public Integer checkActMe_sp = 0;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_head);

        sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("dataCheckActHead", checkActHead_sp);
        editor.putInt("dataCheckActFollow", checkActFollow_sp);
        editor.putInt("dataCheckActAll", checkActAll_sp);
        editor.putInt("dataCheckActMe", checkActMe_sp);
        editor.commit();

        SharedPreferences sp = this.getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        role_sp = sp.getInt("dataRole", 0);
        listActHead = (ListView) findViewById(R.id.listActHead);
        new load().execute(username_sp);

        ImageView exit = (ImageView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role_sp == 1){
                    BackToMain();
                }
                else {
                    BackToMain2();
                }
            }
        });
    }

    public class load extends AsyncTask<String,Void,JSONArray>
    {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActHeadActivity.this);
            progressDialog.setMessage("กำลังโหลด...");
            progressDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("stdID_A", params[0])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/showActHead.php").post(body).build();

            try {
                Response response = oktest.newCall(request).execute();
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
            progressDialog.dismiss();

            id_act_A = new ArrayList<>();
            subAct_A = new ArrayList<>();
            detailAct_A = new ArrayList<>();
            startT_A = new ArrayList<>();
            endT_A = new ArrayList<>();
            startD_A = new ArrayList<>();
            endD_A = new ArrayList<>();
            location_A = new ArrayList<>();
            followJoin_A = new ArrayList<>();
            picAct_A = new ArrayList<>();
            nameClub_A = new ArrayList<>();

            for(int i = 0; i<j.length();i++){
                try {
                    String id_act = j.getJSONObject(i).getString("id_act");
                    String subAct = j.getJSONObject(i).getString("subAct");
                    String detailAct = j.getJSONObject(i).getString("detailAct");
                    String startT = j.getJSONObject(i).getString("startT");
                    String endT = j.getJSONObject(i).getString("endT");
                    String startD = j.getJSONObject(i).getString("startD");
                    String endD = j.getJSONObject(i).getString("endD");
                    String location = j.getJSONObject(i).getString("location");
                    String followJoin = j.getJSONObject(i).getString("followJoin");
                    String picAct = j.getJSONObject(i).getString("picAct");
                    String nameClub = j.getJSONObject(i).getString("nameClub");

                    Log.d("sub",subAct);

                    id_act_A.add(id_act);
                    subAct_A.add(subAct);
                    detailAct_A.add(detailAct);
                    startT_A.add(startT);
                    endT_A.add(endT);
                    startD_A.add(startD);
                    endD_A.add(endD);
                    location_A.add(location);
                    followJoin_A.add(followJoin);
                    picAct_A.add(picAct);
                    nameClub_A.add(nameClub);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CustomList2 customList2 = new CustomList2(getBaseContext(), subAct_A, detailAct_A, id_act_A, picAct_A);
                listActHead.setAdapter(customList2);

                listActHead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent data = new Intent(getBaseContext(), ShowDetailActActivity.class);
                        data.putExtra("id_act",id_act_A.get(position));
                        data.putExtra("subAct",subAct_A.get(position));
                        data.putExtra("detailAct",detailAct_A.get(position));
                        data.putExtra("startT",startT_A.get(position));
                        data.putExtra("endT",endT_A.get(position));
                        data.putExtra("startD",startD_A.get(position));
                        data.putExtra("endD",endD_A.get(position));
                        data.putExtra("location",location_A.get(position));
                        data.putExtra("followJoin",followJoin_A.get(position));
                        data.putExtra("picAct",picAct_A.get(position));
                        data.putExtra("nameClub",nameClub_A.get(position));
                        startActivity(data);
                        finish();
                    }
                });
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
}
