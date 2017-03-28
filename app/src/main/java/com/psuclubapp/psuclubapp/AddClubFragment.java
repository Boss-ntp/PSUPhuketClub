package com.psuclubapp.psuclubapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.id.progress;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddClubFragment extends Fragment {


    private static final int REQ_CODE_PICK_IMAGE = 0;
    public String picClub_A = "0";
    public ImageView imageClub_A;

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
        imageClub_A = (ImageView)view.findViewById(R.id.picClub);

        Button addPicClub_A = (Button)view.findViewById(R.id.addPicClub);
        addPicClub_A.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v) {
                Log.d("pic", "11111111");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQ_CODE_PICK_IMAGE);
            }
        });

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

                final String picClub = picClub_A;

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("ท่านต้องการสร้างชมรมดังกล่าวใช่หรือไม่ ?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new add().execute(nameClub, detailClub, stdIDHead, nameHead, phoneHead, nameAj, phoneAj,String.valueOf(id_FHT), String.valueOf(id_FIS), String.valueOf(id_FTE), String.valueOf(id_CoE), picClub);
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
                    picClub_A = AddClubFragment.MyBase64.encode(imageBytes);
                    Log.d("base64_picAct", picClub_A);

                    //decode Pic
                    byte[] decodedString = Base64.decode(picClub_A, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageClub_A.setImageBitmap(decodedByte);
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
                    .add("picClub_A",params[11])
                    .build();

            OkHttpClient oktest = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

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
