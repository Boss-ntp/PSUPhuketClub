package com.psuclubapp.psuclubapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.psuclubapp.psuclubapp.R.id.picClub;

public class EditClubActivity extends AppCompatActivity {

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
    String picClub = null;

    private static final int REQ_CODE_PICK_IMAGE = 0;
    public String picClub_A = "0";
    public ImageView imageClub_A;
    public Button editPicClub;

    Integer role_sp = 0;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club);

        SharedPreferences sp = getSharedPreferences("ConfigUser", Context.MODE_PRIVATE);
        role_sp = sp.getInt("dataRole",0);

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
        picClub = data.getString("picClub");
//        Log.d("xxxx",name);

        final EditText nameClub_A = (EditText)findViewById(R.id.nameClub);
        final EditText detailClub_A = (EditText)findViewById(R.id.detailClub);
        final EditText stdIDHead_A = (EditText)findViewById(R.id.stdIDHead);
        final EditText nameHead_A = (EditText)findViewById(R.id.nameHead);
        final EditText phoneHead_A = (EditText)findViewById(R.id.phoneHead);
        final EditText nameAj_A = (EditText)findViewById(R.id.nameAj);
        final EditText phoneAj_A = (EditText)findViewById(R.id.phoneAj);
        final CheckBox id_FHT_A = (CheckBox)findViewById(R.id.id_FHT);
        final CheckBox id_FIS_A = (CheckBox)findViewById(R.id.id_FIS);
        final CheckBox id_FTE_A = (CheckBox)findViewById(R.id.id_FTE);
        final CheckBox id_CoE_A = (CheckBox)findViewById(R.id.id_CoE);
        imageClub_A = (ImageView)findViewById(R.id.picClub);
        editPicClub = (Button)findViewById(R.id.editPicClub);

        if(role_sp == 5){
            nameClub_A.setEnabled(false);
            stdIDHead_A.setEnabled(false);
            nameHead_A.setEnabled(false);
        }

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

        picClub_A = picClub;
        switch (picClub_A.trim()){
            case "0":
                editPicClub.setText("เพิ่มรูปภาพ");
                imageClub_A.setImageResource(R.drawable.logo);
                break;
            default:
                editPicClub.setText("เปลี่ยนรูปภาพ");
                //decode Pic
                byte[] decodedString = Base64.decode(picClub_A, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageClub_A.setImageBitmap(decodedByte);
        }


        editPicClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pic", "11111111");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQ_CODE_PICK_IMAGE);
            }
        });

        Button editClub = (Button)findViewById(R.id.editClub);
        editClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameClub = nameClub_A.getText().toString();
                detailClub = detailClub_A.getText().toString();
                stdIDHead = stdIDHead_A.getText().toString();
                nameHead = nameHead_A.getText().toString();
                phoneHead = phoneHead_A.getText().toString();
                nameAj = nameAj_A.getText().toString();
                phoneAj = phoneAj_A.getText().toString();

                Boolean FHT = id_FHT_A.isChecked();
                final int id_FHT = (FHT) ? 1 : 0;
                Boolean FIS = id_FIS_A.isChecked();
                final int id_FIS = (FIS) ? 1 : 0;
                Boolean FTE = id_FTE_A.isChecked();
                final int id_FTE = (FTE) ? 1 : 0;
                Boolean CoE = id_CoE_A.isChecked();
                final int id_CoE = (CoE) ? 1 : 0;

                final String picClub = picClub_A;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(EditClubActivity.this); //pop up เด้งถามว่าจะลบมั้ย
                builder2.setTitle("ท่านต้องการแก้ไขชมรมนี้ใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new edit().execute(nameClub, detailClub, stdIDHead, nameHead, phoneHead, nameAj, phoneAj,String.valueOf(id_FHT), String.valueOf(id_FIS), String.valueOf(id_FTE), String.valueOf(id_CoE), id_club, picClub);
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

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Log.d("Bitmap_picAct", String.valueOf(yourSelectedImage));

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();
                    picClub_A = AddClubFragment.MyBase64.encode(imageBytes);
                    Log.d("base64_picAct", picClub_A);

                    //decode Pic
                    byte[] decodedString = Base64.decode(picClub_A, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageClub_A.setImageBitmap(decodedByte);

                    switch (picClub_A.trim()){
                        case "0":
                            editPicClub.setText("เพิ่มรูปภาพ");
                            break;
                        default:
                            editPicClub.setText("เปลี่ยนรูปภาพ");
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

    public Boolean convertStringToBoolean(String s){

        if (Integer.valueOf(s) == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public class edit extends AsyncTask<String,Void,String>
    {

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
                    .add("id_club_A",params[11])
                    .add("picClub_A",params[12])
                    .build();

            OkHttpClient oktest = new OkHttpClient();
            Request request = new Request.Builder().url("http://psuclub.esy.es/PSUClubApp/editClub.php").post(body).build();

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
            CallBack();
        }
    }

    @Override
    public void onBackPressed() {
        CallBack();
    }

    public void CallBack (){
        if(role_sp == 1){
            Intent intent = new Intent(getBaseContext(),ClubAllActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(getBaseContext(),ClubHeadActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
