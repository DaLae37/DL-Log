package com.dalae37.android.dl_log;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class InquiryActivity extends Inquiry {
    Button inquiryButton;
    EditText inputNickname;
    TextView printView;
    ListView logList;
    LinearLayout inquiryLayout, loadingLayout;
    ImageView loadingGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        inquiryLayout = findViewById(R.id.inquiryLayout);
        loadingLayout = findViewById(R.id.loadingLayout);

        logList = findViewById(R.id.logList);
        printView = findViewById(R.id.printView);
        inputNickname = findViewById(R.id.inputNickname);
        inputNickname.setText(DL_Manager.getInstance().getNickname());
        inquiryButton = findViewById(R.id.inquiryButton);
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern validating = Pattern.compile("^[0-9\\\\p{L} _\\\\.]+$");
                Matcher matcher = validating.matcher(inputNickname.getText().toString());
                if(!matcher.find()){
                    name = inputNickname.getText().toString();
                    isFinish = false;
                    progress = 0;
                    userInfo_thread = new Thread(userInfo_runnable);
                    userInfo_thread.start();
                    checkProgress_thread = new Thread(checkProgress_runnable);
                    checkProgress_thread.start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"잘못된 닉네임입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadingGIF = findViewById(R.id.loadingGIF);
        Glide.with(this).load(R.raw.loading).into(loadingGIF);

    }

    final Handler printHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1 :
                    inquiryLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);
                    if(!isSummoner) {
                        printView.setText("유저를 찾을 수 없습니다.");

                        isFinish = true;
                    }
                    break;
                case 2:
                    PrintData();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 37 :
                    PrintLog();
                    inquiryLayout.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                default :
                    break;
            }
        }
    };
    Thread checkProgress_thread;
    final Runnable checkProgress_runnable = new Runnable() {
        @Override
        public void run() {

            int beforeProgress = 0;
            do{
             if(beforeProgress != progress){
                 beforeProgress = progress;
                 Message message = Message.obtain();
                 message.what = progress;
                 printHandler.sendMessage(message);
             }
            }while(!isFinish);
            Message message = Message.obtain();
            message.what = 37;
            printHandler.sendMessage(message);
        }
    };

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
    public void PrintLog(){
        if(isSummoner) {
            MatchAdapter adapter = new MatchAdapter(this, R.layout.match_item, matchDetails);
            logList.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), FunctionActivity.class);
        startActivity(intent);
        finish();
    }
}