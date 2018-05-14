package com.graduation.one.graduation.acyivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.graduation.one.graduation.R;

/**
 * Created by 666 on 2018/3/12.
 */

public class Student extends AppCompatActivity  {
    private Button myMessage1;
    private Button insertMessage1;
    private Button selectAchievement1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
    }

    private void initView() {
        myMessage1= (Button) findViewById(R.id.myMessage);
        insertMessage1= (Button) findViewById(R.id.insertMessage);
        selectAchievement1= (Button) findViewById(R.id.selectAchievement);
        onClick();
    }

//设置按钮点击事件
    public void onClick() {
        //我的信息
        myMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyMessage.class));
            }
        });
        //添加获奖信息
        insertMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Qingjia .class));
            }
        });

        //成绩查询
        selectAchievement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectAchievement.class));
            }
        });


    }
}
