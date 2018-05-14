package com.graduation.one.graduation.acyivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 666 on 2018/3/13.
 */

public class Insert_credit extends AppCompatActivity {
    private EditText et_course;
    private EditText et_sid;
    private EditText et_credit;
    private Button comitStudent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_graid);
        initView();

    }

    private void initView() {
        et_course= (EditText) findViewById(R.id.et_course);
        et_credit= (EditText) findViewById(R.id.et_credit);
        et_sid= (EditText) findViewById(R.id.et_sid);
        comitStudent= (Button) findViewById(R.id.comitStudent);
        initData();
    }

    private void initData() {
        final String course=et_course.getContext().toString().trim();
        final String sid= et_sid.getContext().toString().trim();
        final String credit=  et_credit.getContext().toString().trim();
        comitStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post()
                        .url("http://140.143.26.74:8080/insert_credit/login.do")
                        .addParams("courseId",course)
                        .addParams("className",sid)
                        .addParams("credit",credit)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(getApplicationContext(),"提交成功",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

}
