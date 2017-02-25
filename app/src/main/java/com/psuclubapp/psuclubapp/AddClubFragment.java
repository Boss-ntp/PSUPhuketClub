package com.psuclubapp.psuclubapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.text.BreakIterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.id.progress;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddClubFragment extends Fragment {


    public AddClubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_club, container, false);

        final EditText nameClub_A = (EditText)view.findViewById(R.id.nameClub);
        final EditText detailClub_A = (EditText)view.findViewById(R.id.detailClub);
        final EditText stdIDHead_A = (EditText)view.findViewById(R.id.stdIDHead);
        final EditText nameHead_A = (EditText)view.findViewById(R.id.nameHead);
        final EditText phoneHead_A = (EditText)view.findViewById(R.id.phoneHead);
        final EditText nameAj_A = (EditText)view.findViewById(R.id.nameAj);
        final EditText phoneAj_A = (EditText)view.findViewById(R.id.phoneAj);

        final CheckBox id_FHT_A = (CheckBox)view.findViewById(R.id.id_FHT);
        final CheckBox id_FIS_A = (CheckBox)view.findViewById(R.id.id_FIS);
        final CheckBox id_FTE_A = (CheckBox)view.findViewById(R.id.id_FTE);
        final CheckBox id_CoE_A = (CheckBox)view.findViewById(R.id.id_CoE);


        Button addClub_A = (Button)view.findViewById(R.id.addClub);
        addClub_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameClub = nameClub_A.getText().toString();
                final String detailClub = detailClub_A.getText().toString();
                final String stdIDHead = stdIDHead_A.getText().toString();
                final String nameHead = nameHead_A.getText().toString();
                final String phoneHead = phoneHead_A.getText().toString();
                final String nameAj = nameAj_A.getText().toString();
                final String phoneAj = phoneAj_A.getText().toString();

                Boolean FHT = id_FHT_A.isChecked();
                final int id_FHT = (FHT) ? 1 : 0;
                Boolean FIS = id_FIS_A.isChecked();
                final int id_FIS = (FIS) ? 1 : 0;
                Boolean FTE = id_FTE_A.isChecked();
                final int id_FTE = (FTE) ? 1 : 0;
                Boolean CoE = id_CoE_A.isChecked();
                final int id_CoE = (CoE) ? 1 : 0;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("ท่านต้องการสร้างชมรมดังกล่าวใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new add().execute(nameClub, detailClub, stdIDHead, nameHead, phoneHead, nameAj, phoneAj,String.valueOf(id_FHT), String.valueOf(id_FIS), String.valueOf(id_FTE), String.valueOf(id_CoE));
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
                    .build();

            OkHttpClient oktest = new OkHttpClient();
            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/addClub.php").post(body).build();

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
            Intent intent = new Intent(getActivity(),ClubAllActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }
    public void BackToMain (){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
