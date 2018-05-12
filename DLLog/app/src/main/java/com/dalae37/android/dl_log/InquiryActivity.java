package com.dalae37.android.dl_log;

import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONObject;

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
    TextView printView;

    final private String url = "https://kr.api.riotgames.com";
    final private String API_KEY = "?api_key=RGAPI-b75df511-8e5c-4d56-81d7-fded3d502af6";
    final private String INFO_PAGE = "/lol/summoner/v3/summoners/by-name/";
    final private String LEAGUE_PAGE = "/lol/league/v3/positions/by-summoner/";

    String name;
    long summonerLevel, id;

    boolean isUnrank, isFlexrank;
    String soloRank, flexRank, soloTier, flexTier, soloLeagueName, flexLeagueName;
    int soloWins,flexWins, soloLose, flexLose, soloPoint, flexPoint;
    String userInfo_json;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1 :
                    ParsingUserInfo();
                    userLeague_thread.start();
                    break;
                case 2:
                    ParsingUserLeague();
                    PrintData();
                    break;
                default :
                    break;
            }
        }
    };

    final Thread userInfo_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                URL fullUrl = new URL(url + INFO_PAGE + inputNickname.getText().toString() + API_KEY);
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

                userInfo_json = sb.toString().trim();

                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getUserInfo", e.toString());
            }
        }
    });
    String userLeague_json;
    final Thread userLeague_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                URL fullUrl = new URL(url + LEAGUE_PAGE + String.valueOf(id) + API_KEY);
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

                userLeague_json = sb.toString().trim();
                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getLeagueInfo", e.toString());
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        printView = (TextView)findViewById(R.id.printView);
        inputNickname = (EditText)findViewById(R.id.inputNickname);
        inquiryButton = (Button)findViewById(R.id.inquiryButton);
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern validating = Pattern.compile("^[0-9\\\\p{L} _\\\\.]+$");
                Matcher matcher = validating.matcher(inputNickname.getText().toString());
                if(!matcher.find()){
                    userInfo_thread.start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"잘못된 닉네임입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void PrintData(){
        StringBuffer sb = new StringBuffer();
        sb.append("닉네임 : " + name + "\n" + "레벨 : " + summonerLevel + "\n");
        if(isUnrank){
            sb.append("언랭크");
        }
        else{
            sb.append("솔로랭크\n리그 : " + soloLeagueName + "\n티어 : " + soloTier + " " + soloRank + "\t" + soloPoint + "점" + "\n" + "승 : " + soloWins + " 패 : " + soloLose + "\n");
            if(isFlexrank){
                sb.append("자유랭크\n리그 : " + flexLeagueName + "\n티어 : " + flexTier + " " + flexRank + "\t" + flexPoint + "점" + "\n" + "승 : " + flexWins + " 패 : " + flexLose + "\n");
            }
        }
        printView.setText(sb.toString());
    }

    public void ParsingUserInfo(){
        try {
            JSONObject jObject = new JSONObject(userInfo_json);
            name = jObject.getString("name");
            summonerLevel = jObject.getLong("summonerLevel");
            id = jObject.getLong("id");
        }
        catch (Exception e){
            Log.e("ParsingUserInfo", e.toString());
        }
    }

    public void ParsingUserLeague(){
        if(userLeague_json.equals("[]")){
            isUnrank = true;
        }
        else {
            try {
                JSONArray jArray = new JSONArray(userLeague_json);
                JSONObject jObject;
                if (jArray.length() != 1) {
                    jObject = jArray.getJSONObject(0);
                    soloLeagueName = jObject.getString("leagueName");
                    soloTier = jObject.getString("tier");
                    soloRank = jObject.getString("rank");
                    soloPoint = jObject.getInt("leaguePoints");
                    soloWins = jObject.getInt("wins");
                    soloLose = jObject.getInt("losses");
                    jObject = jArray.getJSONObject(1);
                    flexLeagueName = jObject.getString("leagueName");
                    flexTier = jObject.getString("tier");
                    flexRank = jObject.getString("rank");
                    flexPoint = jObject.getInt("leaguePoints");
                    flexWins = jObject.getInt("wins");
                    flexLose = jObject.getInt("losses");
                    isFlexrank = true;
                } else {
                    jObject = jArray.getJSONObject(0);
                    soloLeagueName = jObject.getString("leagueName");
                    soloTier = jObject.getString("tier");
                    soloRank = jObject.getString("rank");
                    soloPoint = jObject.getInt("leaguePoints");
                    soloWins = jObject.getInt("wins");
                    soloLose = jObject.getInt("losses");
                    isFlexrank = false;
                }
            } catch (Exception e) {
                Log.e("Array", e.toString());
            }
        }
    }
}
