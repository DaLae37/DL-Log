package com.dalae37.android.dl_log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button logIN;
    EditText inputID,inputPW;
    TextView findID, findPW, signUP, nonMember;
    String loginResult;
    SharedPreferences sp;
    boolean isTryLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIN = findViewById(R.id.logIN);
        logIN.setOnClickListener(this);

        inputID = findViewById(R.id.inputID);
        inputPW = findViewById(R.id.inputPW);

        findID = findViewById(R.id.findID);
        findID.setOnClickListener(this);
        findPW = findViewById(R.id.findPW);
        findPW.setOnClickListener(this);
        signUP = findViewById(R.id.signUP);
        signUP.setOnClickListener(this);
        nonMember = findViewById(R.id.nonMember);
        nonMember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logIN :
                if(!isTryLogin){
                    isTryLogin = true;
                    loginThread = new Thread(loginRunnable);
                    loginThread.start();
                }
                //String id = inputID.getText().toString();
                //String pw = inputPW.getText().toString();
                //if(CheckIDandPW(id,pw)){
                //    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                    //FunctionActivity();
                //}
                break;
            case R.id.signUP :
                Intent intent = new Intent(getApplicationContext(), SignUP_Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.findID :
                break;
            case R.id.findPW :
                break;
            case R.id.nonMember :
                FunctionActivity();
                break;
            default :
                break;
        }
    }

    public void CheckLoginResult(){
        try {
            JSONObject jObject = new JSONObject(loginResult);
            String success = jObject.getString("success"), message = jObject.getString("message");
            if(success.equals("true")){
                DL_Manager.getInstance().setNickname(message);
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                FunctionActivity();
            }
            else{
                Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀립니다", Toast.LENGTH_SHORT).show();
            }
            isTryLogin = false;
        }
        catch (Exception e){
            Log.e("CheckLoginResult",e.toString());
        }
    }
    /*public boolean CheckIDandPW(String id, String pw){
        MemberDB helper = new MemberDB(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, pw from member_tb",null);
        boolean hasSign = false;
        for(int i=0; i<cursor.getCount(); i++){
            cursor.moveToNext();
            hasSign = (cursor.getString(0).equals(id)) && (cursor.getString(1).equals(pw));
        }
        return hasSign;
    }*/

    public void FunctionActivity(){
        Intent intent = new Intent(getApplicationContext(), FunctionActivity.class);
        startActivity(intent);
        finish();
    }

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한 번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
    final Handler threadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CheckLoginResult();
                    break;
            }
        }
    };

    Thread loginThread;
    final Runnable loginRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                String urlParameters  = "id=" + inputID.getText().toString() + "&password=" +inputPW.getText().toString();
                byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int postDataLength = postData.length;
                URL fullUrl = new URL("http://soylatte.kr:8681/login");
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

                loginResult = sb.toString().trim();
                Message message = Message.obtain();
                message.what = 1;
                threadHandler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getLogInfo", e.toString());
                isTryLogin = false;
            }
        }
    };
}