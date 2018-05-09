package com.dalae37.android.dl_log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUP_Activity extends AppCompatActivity implements View.OnClickListener{
    Button save, close;
    EditText id,pw,nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        id = (EditText)findViewById(R.id.idInput);
        pw = (EditText)findViewById(R.id.pwInput);
        nick = (EditText)findViewById(R.id.nicknameInput);

        save = (Button)findViewById(R.id.saveSignUP);
        save.setOnClickListener(this);
        close = (Button)findViewById(R.id.closeSignUP);
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

                Toast.makeText(getApplicationContext(),R.string.successCreateID, Toast.LENGTH_SHORT).show();

                CloseSignUPActivity();
                break;
            case R.id.closeSignUP :
                CloseSignUPActivity();
                break;
        }
    }

    public void CloseSignUPActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
