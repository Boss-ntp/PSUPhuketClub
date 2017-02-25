package com.psuclubapp.psuclubapp;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class  AddActFragment extends Fragment {

    public String username_sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_act, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        username_sp = sp.getString("dataUsername", null);

        final EditText subAct_A = (EditText)view.findViewById(R.id.subAct);
        final EditText detailAct_A = (EditText)view.findViewById(R.id.detailAct);
        final EditText startT_A = (EditText)view.findViewById(R.id.startT);
        final EditText endT_A = (EditText)view.findViewById(R.id.endT);
        final EditText startD_A = (EditText)view.findViewById(R.id.startD);
        final EditText endD_A = (EditText)view.findViewById(R.id.endD);
        final EditText location_A = (EditText)view.findViewById(R.id.location);
        final String stdID_A = username_sp;

        Button addAct_A = (Button)view.findViewById(R.id.addAct);
        addAct_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String subAct = subAct_A.getText().toString();
                final String detailAct = detailAct_A.getText().toString();
                final String startT = startT_A.getText().toString();
                final String endT = endT_A.getText().toString();
                final String startD = startD_A.getText().toString();
                final String endD = endD_A.getText().toString();
                final String location = location_A.getText().toString();
                final String stdID = stdID_A;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("ท่านต้องการสร้างกิจกรรมดังกล่าวใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new add().execute(subAct,detailAct,startT,endT,startD,endD,location,stdID);
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


        return view;
    }

    public class add extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "กรุณารอสักครู่", "กำลังอัพโหลด...");
        }

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("subAct_A",params[0])
                    .add("detailAct_A", params[1])
                    .add("startT_A",params[2])
                    .add("endT_A",params[3])
                    .add("startD_A",params[4])
                    .add("endD_A",params[5])
                    .add("location_A",params[6])
                    .add("stdID_A",params[7])
                    .build();

            OkHttpClient oktest = new OkHttpClient();
            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/addActivity.php").post(body).build();

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
        BackToMain();
    }

    }
    public void BackToMain (){
        Intent intent = new Intent(getActivity(),ActHeadActivity.class);
        startActivity(intent);
    }

}
