package com.dalae37.android.dl_log;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button logIN;
    EditText inputID,inputPW;
    TextView findID, findPW, signIN, nonMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIN = (Button)findViewById(R.id.logIN);
        logIN.setOnClickListener(this);

        inputID = (EditText)findViewById(R.id.inputID);
        inputPW = (EditText)findViewById(R.id.inputPW);

        findID = (TextView)findViewById(R.id.findID);
        findID.setOnClickListener(this);
        findPW = (TextView)findViewById(R.id.findPW);
        findPW.setOnClickListener(this);
        signIN = (TextView)findViewById(R.id.signIN);
        signIN.setOnClickListener(this);
        nonMember = (TextView)findViewById(R.id.nonMember);
        nonMember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logIN :
                String id = inputID.getText().toString();
                String pw = inputPW.getText().toString();

                break;
            case R.id.signIN :
                Intent intent = new Intent(getApplicationContext(), SignIN_Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.findID :
                break;
            case R.id.findPW :
                break;
            case R.id.nonMember :
                break;
            default :
                break;
        }
    }

    public boolean CheckID(String id){
        return false;
    }
    public boolean CheckPW(String pw){
        return false;
    }
}
