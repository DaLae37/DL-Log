package com.dalae37.android.dl_log;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Inquiry extends AppCompatActivity implements  API_Urls{
    ArrayList<Match> matches;
    ArrayList<MatchDetail> matchDetails;

    Thread userInfo_thread, userLeague_thread, userLog_thread, matchDetail_thread;
    long summonerLevel, id, accountId;

    boolean isSummoner, isUnrank, isFlexrank, isFinish;
    String soloRank, flexRank, soloTier, flexTier, soloLeagueName, flexLeagueName, name;
    int soloWins,flexWins, soloLose, flexLose, soloPoint, flexPoint, progress, matchesIndex, matchesFinishIndex;
    String userInfo_json, userLeague_json, userLog_json;

    final Handler threadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progress = msg.what;
            switch (msg.what){
                case 1 :
                    if(isSummoner){
                        ParsingUserInfo();
                        userLeague_thread = new Thread(userLeague_runnable);
                        userLeague_thread.start();
                    }
                    break;
                case 2:
                    ParsingUserLeague();
                    userLog_thread = new Thread(userLog_runnable);
                    userLog_thread.start();
                    break;
                case 3:
                    ParsingUserLog();
                    GetMatchDetail();
                    break;
                case 4:
                    if(matchesIndex != matchesFinishIndex) {
                        matchDetail_thread = new Thread(matchDeatil_runnable);
                        matchDetail_thread.start();
                    }else{
                        isFinish = true;
                    }
                    break;
                default :
                    break;
            }
        }
    };

    final Runnable userInfo_runnable = new Runnable() {
        @Override
        public void run() {
            try{
                URL fullUrl = new URL(url + INFO_PAGE + name + API_KEY);
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
                    isSummoner = true;
                }
                else{
                    inputStream = conn.getErrorStream();
                    isSummoner = false;
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
                threadHandler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getUserInfo", e.toString());
            }
        }
    };
    final Runnable userLeague_runnable = new Runnable(){
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
                threadHandler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getLeagueInfo", e.toString());
            }
        }
    };
    final Runnable userLog_runnable = new Runnable() {
        @Override
        public void run() {
            try{
                URL fullUrl = new URL(url + MATCH_PAGE + String.valueOf(accountId) + API_KEY);
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

                userLog_json = sb.toString().trim();
                Message message = Message.obtain();
                message.what = 3;
                threadHandler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getLogInfo", e.toString());
            }
        }
    };
    final Runnable matchDeatil_runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL fullUrl = new URL(url + MATCH_DETAIL_PAGE + String.valueOf(matches.get(matchesIndex++).gameId) + API_KEY);
                HttpsURLConnection conn = (HttpsURLConnection) fullUrl.openConnection();
                conn.setConnectTimeout(3000); //응답시간 3초
                conn.setReadTimeout(3000); //Read연결시간
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.connect();
                int responStatusCode = conn.getResponseCode();
                InputStream inputStream;
                if (responStatusCode == conn.HTTP_OK) {
                    inputStream = conn.getInputStream();
                } else {
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
                MatchDetail detail = new MatchDetail(sb.toString().trim(), accountId);
                matchDetails.add(detail);

                Message message = Message.obtain();
                message.what = 4;
                threadHandler.sendMessage(message);
            }
            catch (Exception e){
                Log.e("getLogInfo", e.toString());
            }
        }
    };
    public void ParsingUserLog(){
        matches = new ArrayList<>();
        try{
            JSONObject jObject = new JSONObject(userLog_json);
            JSONArray jArray = jObject.getJSONArray("matches");
            int length = jArray.length();
            for(int i=0; i<length; i++) {
                JSONObject object = jArray.getJSONObject(i);
                Match m = new Match(object.getString("lane"), object.getLong("gameId"), object.getInt("champion"), object.getLong("timestamp"), object.getInt("queue"), object.getString("role"), object.getInt("season"));
                matches.add(m);
            }
        }
        catch (Exception e){
            Log.e("ParsingUserLog", e.toString());
        }
    }
    public void ParsingUserInfo(){
        try {
            JSONObject jObject = new JSONObject(userInfo_json);
            name = jObject.getString("name");
            summonerLevel = jObject.getLong("summonerLevel");
            id = jObject.getLong("id");
            accountId = jObject.getLong("accountId");
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
            isUnrank = false;
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

    public void GetMatchDetail(){
        matchDetails = new ArrayList<>();
        int length = matches.size();
        matchesFinishIndex = 9;
        matchesIndex = 0;

        Message message = Message.obtain();
        message.what = 4;
        threadHandler.sendMessage(message);
    }
}