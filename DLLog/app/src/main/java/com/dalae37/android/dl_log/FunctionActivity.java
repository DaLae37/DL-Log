package com.dalae37.android.dl_log;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FunctionActivity extends AppCompatActivity {
    Button toInquiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        toInquiry = (Button)findViewById(R.id.toInquiry);
        toInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InquiryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
