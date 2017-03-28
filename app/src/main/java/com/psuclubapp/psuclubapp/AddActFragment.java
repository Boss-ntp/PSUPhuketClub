package com.psuclubapp.psuclubapp;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class  AddActFragment extends Fragment {

    private static final int REQ_CODE_PICK_IMAGE = 0;
    public String picAct_A = "0";
    public ImageView imageAct_A;
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
        final Switch followJoin_A = (Switch)view.findViewById(R.id.followJoin);
        imageAct_A = (ImageView)view.findViewById(R.id.picAct);
        final String stdID_A = username_sp;

        Button addPicAct_A = (Button)view.findViewById(R.id.addPicAct);
        addPicAct_A.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v) {
                Log.d("pic", "11111111");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQ_CODE_PICK_IMAGE);
            }
        });

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
                final String picAct = picAct_A;
                final String stdID = stdID_A;

                Boolean checkFollowJoin = followJoin_A.isChecked();
                final int followJoin = (checkFollowJoin) ? 1 : 0;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("ท่านต้องการสร้างกิจกรรมดังกล่าวใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Join",String.valueOf(followJoin));
                        new add().execute(subAct, detailAct, startT, endT, startD, endD, location, String.valueOf(followJoin), picAct, stdID);
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

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Log.d("pic", "22222222");
        switch(requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Log.d("pic", "33333333");
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Log.d("Bitmap_picAct", String.valueOf(yourSelectedImage));

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();
                    picAct_A = MyBase64.encode(imageBytes);
                    Log.d("base64_picAct", picAct_A);

                    //decode Pic
                    byte[] decodedString = Base64.decode(picAct_A, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageAct_A.setImageBitmap(decodedByte);
                }
        }
    }



    public static class MyBase64 {

        private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        private static int[]  toInt   = new int[128];

        static {
            for(int i=0; i< ALPHABET.length; i++){
                toInt[ALPHABET[i]]= i;
            }
        }

        public static String encode(byte[] buf){
            int size = buf.length;
            char[] ar = new char[((size + 2) / 3) * 4];
            int a = 0;
            int i=0;
            while(i < size){
                byte b0 = buf[i++];
                byte b1 = (i < size) ? buf[i++] : 0;
                byte b2 = (i < size) ? buf[i++] : 0;

                int mask = 0x3F;
                ar[a++] = ALPHABET[(b0 >> 2) & mask];
                ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
                ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
                ar[a++] = ALPHABET[b2 & mask];
            }//while

            switch(size % 3){
                case 1: ar[--a]  = '=';
                case 2: ar[--a]  = '=';
            }//switch
            return new String(ar);
        }

        /**
         * Translates the specified Base64 string into a byte array.
         *
         * @param s the Base64 string (not null)
         * @return the byte array (not null)
         */
        public static byte[] decode(String s){
            int delta = s.endsWith( "==" ) ? 2 : s.endsWith( "=" ) ? 1 : 0;
            byte[] buffer = new byte[s.length()*3/4 - delta];
            int mask = 0xFF;
            int index = 0;

            for(int i=0; i< s.length(); i+=4){
                int c0 = toInt[s.charAt( i )];
                int c1 = toInt[s.charAt( i + 1)];
                buffer[index++]= (byte)(((c0 << 2) | (c1 >> 4)) & mask);

                if(index >= buffer.length){
                    return buffer;
                }//if

                int c2 = toInt[s.charAt( i + 2)];
                buffer[index++]= (byte)(((c1 << 4) | (c2 >> 2)) & mask);

                if(index >= buffer.length){
                    return buffer;
                }//if

                int c3 = toInt[s.charAt( i + 3 )];
                buffer[index++]= (byte)(((c2 << 6) | c3) & mask);
            }//for
            return buffer;
        }

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
                    .add("followJoin_A",params[7])
                    .add("picAct_A",params[8])
                    .add("stdID_A",params[9])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

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
