package com.example.wanqian.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanqian.BuildConfig;
import com.example.wanqian.R;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout toolbar_iv;
    private TextView about_tupan;
    private TextView about_tiaokuang;
    private TextView about_zhence;
    private TextView about_banquang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }
    private void initView() {
        toolbar_iv =  findViewById(R.id.toolbar_iv);
        about_tupan =  findViewById(R.id.about_tupan);
        about_tiaokuang =  findViewById(R.id.about_tiaokuang);
        about_zhence = findViewById(R.id.about_zhence);
        about_banquang = findViewById(R.id.about_banquang);
        about_tiaokuang.setOnClickListener(this);
        about_zhence.setOnClickListener(this);
        toolbar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        about_tupan.setText("智能环卫v"+ BuildConfig.VERSION_NAME);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);   //获取年份
        about_banquang.setText("版权所有 ©" +year+ "万谦科技");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_tiaokuang:
                break;
            case R.id.about_zhence:
                break;
        }
    }
}
