package com.dalae37.android.dl_log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class SignUP_Activity extends AppCompatActivity implements View.OnClickListener{
    Button save, close;
    EditText id,pw,nick;
    String registerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        id = findViewById(R.id.idInput);
        pw = findViewById(R.id.pwInput);
        nick = findViewById(R.id.nicknameInput);

        save = findViewById(R.id.saveSignUP);
        save.setOnClickListener(this);
        close = findViewById(R.id.closeSignUP);
        close.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveSignUP :
                MemberDB helper = new MemberDB(getApplicationContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("insert into member_tb (id, pw, nickname) values(?,?,?)", new String[]{id.getText().toString(), pw.getText().toString(), nick.getText().toString()});
                db.close();

                registerThread = new Thread(registerRunnable);
                registerThread.start();
                break;
            case R.id.closeSignUP :
                CloseSignUPActivity();
                break;
        }
    }

    public void CheckRegisterResult(){
        try {
            JSONObject jObject = new JSONObject(registerResult);
            String success = jObject.getString("success"), message = jObject.getString("message");
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
            if(success.equals("true")){
                CloseSignUPActivity();
            }
        }
        catch (Exception e){
            Log.e("CheckRegisterResult", e.toString());
        }
    }
    public void CloseSignUPActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CloseSignUPActivity();
    }
    final Handler threadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CheckRegisterResult();
                    break;
            }
        }
    };

    Thread registerThread;
    final Runnable registerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                String urlParameters  = "username=" + nick.getText().toString() +"&id=" + id.getText().toString() +
                        "&password=" +pw.getText().toString();
                byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int postDataLength = postData.length;
                URL fullUrl = new URL("http://soylatte.kr:8681/register");
                HttpURLConnection conn = (HttpURLConnection) fullUrl.openConnection();

                conn.setConnectTimeout(3000); //응답시간 3초
                conn.setReadTimeout(3000); //Read연결시간
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.write( postData );
                }

                int responStatusCode = conn.getResponseCode();
                InputStream inputStream;
                if (responStatusCode == conn.HTTP_OK) {
                    inputStream = conn.getInputStream();
                }
                else {
                    inputStream = conn.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                conn.disconnect();

                registerResult = sb.toString().trim();
                Message message = Message.obtain();
                message.what = 1;
                threadHandler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getLogInfo", e.toString());
            }
        }
    };
}
