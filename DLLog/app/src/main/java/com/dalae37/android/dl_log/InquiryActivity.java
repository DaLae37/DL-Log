package com.dalae37.android.dl_log;

import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class InquiryActivity extends AppCompatActivity {
    Button inquiryButton;
    EditText inputNickname;

    final private String url = "https://kr.api.riotgames.com";
    final private String API_KEY = "?api_key=RGAPI-7de46ddc-ef9a-4b42-90e7-9a6edaf351b0";
    final private String PAGE = "/lol/summoner/v3/summoners/by-name/";

    String result;
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                URL fullUrl = new URL(url + PAGE + inputNickname.getText().toString() + API_KEY);
                HttpsURLConnection conn = (HttpsURLConnection) fullUrl.openConnection();
                conn.setConnectTimeout(3000); //응답시간 3초
                conn.setReadTimeout(3000); //Read연결시간
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.connect();
                int responStatusCode = conn.getResponseCode();
                InputStream inputStream;
                if(responStatusCode == conn.HTTP_OK){
                    inputStream = conn.getInputStream();
                }
                else{
                    inputStream = conn.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                conn.disconnect();

                result = sb.toString().trim();
                Log.e("e",result);
            }
            catch (Exception e){
                Log.e("inquiryActivity", e.toString());
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        inputNickname = (EditText)findViewById(R.id.inputNickname);
        inquiryButton = (Button)findViewById(R.id.inquiryButton);
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern validating = Pattern.compile("^[0-9\\\\p{L} _\\\\.]+$");
                Matcher matcher = validating.matcher(inputNickname.getText().toString());
                if(!matcher.find()){
                    Log.e("쓰레드","성공");
                    thread.start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"잘못된 닉네임입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
