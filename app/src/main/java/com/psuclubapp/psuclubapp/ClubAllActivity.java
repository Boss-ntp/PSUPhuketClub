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

public class ClubAllActivity extends AppCompatActivity {

    public ArrayList<String> nameClub_A = new ArrayList<>();
    public ArrayList<String> detailClub_A = new ArrayList<>();
    public ArrayList<String> stdIDHead_A = new ArrayList<>();
    public ArrayList<String> nameHead_A = new ArrayList<>();
    public ArrayList<String> phoneHead_A = new ArrayList<>();
    public ArrayList<String> nameAj_A = new ArrayList<>();
    public ArrayList<String> phoneAj_A = new ArrayList<>();
    public ArrayList<String> id_FHT_A = new ArrayList<>();
    public ArrayList<String> id_FIS_A = new ArrayList<>();
    public ArrayList<String> id_FTE_A = new ArrayList<>();
    public ArrayList<String> id_CoE_A = new ArrayList<>();
    public ArrayList<String> id_club_A = new ArrayList<>();
    public ArrayList<String> picClub_A = new ArrayList<>();
    public ListView listViewAll;

    public String username_sp = null;
    public Integer role_sp = 0;
    public Integer checkHead_sp = 0;
    public Integer checkAll_sp = 1;
    public Integer checkFollow_sp = 0;
    public Integer checkMe_sp = 0;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_all);

        sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("dataCheckHead", checkHead_sp);
        editor.putInt("dataCheckAll", checkAll_sp);
        editor.putInt("dataCheckFollow", checkFollow_sp);
        editor.putInt("dataCheckMe", checkMe_sp);
        editor.commit();

        SharedPreferences sp = this.getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);
        role_sp = sp.getInt("dataRole", 0);
        listViewAll = (ListView) findViewById(R.id.clubAll);
        new showClubAll().execute(username_sp);

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

    public class showClubAll extends AsyncTask<String, Void, JSONArray> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ClubAllActivity.this);
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

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/showClubAll.php").post(body).build();

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

            nameClub_A = new ArrayList<>();
            detailClub_A = new ArrayList<>();
            stdIDHead_A = new ArrayList<>();
            nameHead_A = new ArrayList<>();
            phoneHead_A = new ArrayList<>();
            nameAj_A = new ArrayList<>();
            phoneAj_A = new ArrayList<>();
            id_FHT_A = new ArrayList<>();
            id_FIS_A = new ArrayList<>();
            id_FTE_A = new ArrayList<>();
            id_CoE_A = new ArrayList<>();
            id_club_A = new ArrayList<>();
            picClub_A = new ArrayList<>();

            for (int i = 0; i < j.length(); i++) {
                try {
                    String nameClub = j.getJSONObject(i).getString("nameClub");
                    String detailClub = j.getJSONObject(i).getString("detailClub");
                    String stdIDHead = j.getJSONObject(i).getString("stdIDHead");
                    String nameHead = j.getJSONObject(i).getString("nameHead");
                    String phoneHead = j.getJSONObject(i).getString("phoneHead");
                    String nameAj = j.getJSONObject(i).getString("nameAj");
                    String phoneAj = j.getJSONObject(i).getString("phoneAj");
                    String id_club = j.getJSONObject(i).getString("id_club");
                    String id_FHT = j.getJSONObject(i).getString("fac_fht");
                    String id_FIS = j.getJSONObject(i).getString("fac_fis");
                    String id_FTE = j.getJSONObject(i).getString("fac_fte");
                    String id_CoE = j.getJSONObject(i).getString("fac_coe");
                    String picClub = j.getJSONObject(i).getString("picClub");

                    Log.d("name ", nameClub);
                    // Log.d("fac ",id_fac.toString());

                    nameClub_A.add(nameClub);
                    detailClub_A.add(detailClub);
                    stdIDHead_A.add(stdIDHead);
                    nameHead_A.add(nameHead);
                    phoneHead_A.add(phoneHead);
                    nameAj_A.add(nameAj);
                    phoneAj_A.add(phoneAj);
                    id_FHT_A.add(id_FHT);
                    id_FIS_A.add(id_FIS);
                    id_FTE_A.add(id_FTE);
                    id_CoE_A.add(id_CoE);
                    id_club_A.add(id_club);
                    picClub_A.add(picClub);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CustomList customList = new CustomList(getBaseContext(), nameClub_A, detailClub_A, id_club_A, picClub_A);
                listViewAll.setAdapter(customList);

                listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent data = new Intent(getBaseContext(), ShowDetailClubActivity.class);
                        data.putExtra("nameClub", nameClub_A.get(position));
                        data.putExtra("detailClub", detailClub_A.get(position));
                        data.putExtra("stdIDHead", stdIDHead_A.get(position));
                        data.putExtra("nameHead", nameHead_A.get(position));
                        data.putExtra("phoneHead", phoneHead_A.get(position));
                        data.putExtra("nameAj", nameAj_A.get(position));
                        data.putExtra("phoneAj", phoneAj_A.get(position));
                        data.putExtra("id_FHT", id_FHT_A.get(position));
                        data.putExtra("id_FIS", id_FIS_A.get(position));
                        data.putExtra("id_FTE", id_FTE_A.get(position));
                        data.putExtra("id_CoE", id_CoE_A.get(position));
                        data.putExtra("id_club", id_club_A.get(position));
                        data.putExtra("picClub",picClub_A.get(position));
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
