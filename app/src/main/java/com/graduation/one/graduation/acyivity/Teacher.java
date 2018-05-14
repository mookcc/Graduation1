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
 * 教师主界面
 */

public class Teacher extends AppCompatActivity {
    private Button myClass;
    private Button selectInsert;
    private Button insertClass;
    private Button qingjia;
    private Button student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        initView();
    }

    private void initView() {
        myClass = (Button) findViewById(R.id.myClass);
        selectInsert = (Button) findViewById(R.id.selectInsert);
        insertClass = (Button) findViewById(R.id.insertClass);
        qingjia = (Button) findViewById(R.id.bt_qingjia);
        student= (Button) findViewById(R.id.selectStudent);
        onClick();
    }


    public void onClick() {

        myClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MyClass.class);
                intent.putExtra("code","0");
                startActivity(intent);
            }
        });
        selectInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),qingjiachaxun .class));
            }
        });
        insertClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Insert_credit.class));
            }
        });
        qingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InsertClass .class));
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MyClass.class);
                intent.putExtra("code","1");
                startActivity(intent);
            }
        });

    }

}
