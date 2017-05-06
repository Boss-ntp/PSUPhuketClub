package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActActivity extends AppCompatActivity {

    String id_act = null;
    String subAct = null;
    String detailAct = null;
    String startD = null;
    String endD = null;
    String startT = null;
    String endT = null;
    String location = null;
    String followJoin = null;
    String picAct = null;

    private static final int REQ_CODE_PICK_IMAGE = 0;
    public String picAct_A = "0";
    public ImageView imageAct_A;
    public Button editPicAct;

    public Integer role_sp = 0;
    public String username_sp = null;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_act);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        role_sp = sp.getInt("dataRole",0);
        username_sp = sp.getString("dataUsername", null);

        Bundle data = getIntent().getExtras();
        id_act = data.getString("id_act");
        subAct = data.getString("subAct");
        detailAct = data.getString("detailAct");
        startD = data.getString("startD");
        endD = data.getString("endD");
        startT = data.getString("startT");
        endT = data.getString("endT");
        location = data.getString("location");
        followJoin = data.getString("followJoin");
        picAct = data.getString("picAct");
        Log.d("picAct", picAct);

        final EditText subAct_A = (EditText) findViewById(R.id.subAct);
        final EditText detailAct_A = (EditText) findViewById(R.id.detailAct);
        final EditText startD_A = (EditText) findViewById(R.id.startD);
        final EditText endD_A = (EditText) findViewById(R.id.endD);
        final EditText startT_A = (EditText) findViewById(R.id.startT);
        final EditText endT_A = (EditText) findViewById(R.id.endT);
        final EditText location_A = (EditText) findViewById(R.id.location);
        final Switch followJoin_A = (Switch) findViewById(R.id.followJoin);
        imageAct_A = (ImageView)findViewById(R.id.picAct);
        editPicAct = (Button)findViewById(R.id.editPicAct);

        subAct_A.setText(subAct);
        detailAct_A.setText(detailAct);
        startD_A.setText(startD);
        endD_A.setText(endD);
        startT_A.setText(startT);
        endT_A.setText(endT);
        location_A.setText(location);
        followJoin_A.setChecked(convertStringToBoolean(followJoin));





        picAct_A = picAct;
        switch (picAct_A.trim()){
            case "0":
                //editPicAct.setText("เพิ่มรูปภาพ");
                //imageAct_A.setImageResource(R.drawable.logo);
                editPicAct.setText("เปลี่ยนรูปภาพ");
                switch (id_act){
                    case "15": imageAct_A.setImageResource(R.drawable.a15); break;
                    case "16": imageAct_A.setImageResource(R.drawable.a16); break;
                    case "29": imageAct_A.setImageResource(R.drawable.a29); break;
                    case "30": imageAct_A.setImageResource(R.drawable.a30); break;
                    case "31": imageAct_A.setImageResource(R.drawable.a31); break;
                    case "32": imageAct_A.setImageResource(R.drawable.a32); break;
                    case "33": imageAct_A.setImageResource(R.drawable.a33); break;
                    default:imageAct_A.setImageResource(R.drawable.logo);
                }
                break;
            default:
                editPicAct.setText("เปลี่ยนรูปภาพ");
                //decode Pic
                byte[] decodedString = Base64.decode(picAct_A, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageAct_A.setImageBitmap(decodedByte);
        }

        editPicAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pic", "11111111");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQ_CODE_PICK_IMAGE);
            }
        });

        Button editAct = (Button)findViewById(R.id.editAct);
        editAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subAct = subAct_A.getText().toString();
                detailAct = detailAct_A.getText().toString();
                startD = startD_A.getText().toString();
                endD = endD_A.getText().toString();
                startT = startT_A.getText().toString();
                endT = endT_A.getText().toString();
                location = location_A.getText().toString();

                Boolean checkFollowJoin = followJoin_A.isChecked();
                final int followJoin = (checkFollowJoin) ? 1 : 0;
                final String picAct = picAct_A;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(EditActActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder2.setTitle("ท่านต้องการแก้ไขกิจกรรมนี้ใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new edit().execute(id_act, subAct, detailAct, startD, endD, startT, endT, location, String.valueOf(followJoin), username_sp, picAct);
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

        Button backToList = (Button)findViewById(R.id.backToList);
        backToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActHeadActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2; // Reduces the image resolution
                    Log.d("filePath",filePath);
                    Log.d("options",String.valueOf(options));
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath, options);
                    Log.d("Bitmap_picAct", String.valueOf(yourSelectedImage));

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();
                    picAct_A = AddClubFragment.MyBase64.encode(imageBytes);
                    Log.d("base64_picAct", picAct_A);

                    //decode Pic
                    byte[] decodedString = Base64.decode(picAct_A, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageAct_A.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,120,120,false));

                    switch (picAct_A.trim()){
                        case "0":
                            editPicAct.setText("เพิ่มรูปภาพ");
                            break;
                        default:
                            editPicAct.setText("เปลี่ยนรูปภาพ");
                    }
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

    public class edit extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("id_act_A",params[0])
                    .add("subAct_A",params[1])
                    .add("detailAct_A", params[2])
                    .add("startD_A",params[3])
                    .add("endD_A",params[4])
                    .add("startT_A",params[5])
                    .add("endT_A",params[6])
                    .add("location_A",params[7])
                    .add("followJoin_A",params[8])
                    .add("stdID_A",params[9])
                    .add("picAct_A",params[10])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();//ใช้ OkHttp ติดต่อกับ server

            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/editAct.php").post(body).build();

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
            Intent intent = new Intent(getBaseContext(), ActHeadActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public Boolean convertStringToBoolean(String s) {

        if (Integer.valueOf(s) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
