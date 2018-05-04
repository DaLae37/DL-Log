package com.dalae37.android.dl_log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button logIN;
    EditText inputID,inputPW;
    TextView findID, findPW, signUP, nonMember;

    SharedPreferences sp;
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
        signUP = (TextView)findViewById(R.id.signUP);
        signUP.setOnClickListener(this);
        nonMember = (TextView)findViewById(R.id.nonMember);
        nonMember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logIN :
                String id = inputID.getText().toString();
                String pw = inputPW.getText().toString();
                if(CheckIDandPW(id,pw)){
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), FunctionActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 틀립니다",Toast.LENGTH_SHORT).show();
                }
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
                break;
            default :
                break;
        }
    }

    public boolean CheckIDandPW(String id, String pw){
        MemberDB helper = new MemberDB(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, pw from member_tb",null);
        boolean hasSign = false;
        for(int i=0; i<cursor.getCount(); i++){
            cursor.moveToNext();
            hasSign = (cursor.getString(0).equals(id)) && (cursor.getString(1).equals(pw));
        }
        return hasSign;
    }
}
