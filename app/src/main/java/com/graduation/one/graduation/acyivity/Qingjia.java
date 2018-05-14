package com.graduation.one.graduation.acyivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 666 on 2018/4/15.
 */

public class Qingjia extends AppCompatActivity {
    private EditText et_teacher;
    private EditText et_classnName;
    private EditText et_massage;
    private Button tijiao;
    private TextView xue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qingjia);
        initView();
        initDta();
    }

    private void initDta() {


        tijiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String studentName=xue.getText().toString();
                final String teacher=et_teacher.getText().toString();
                final String classnName=et_classnName.getText().toString();
                final String massage=et_massage.getText().toString();
                OkHttpUtils
                        .post()
                        .url("http://140.143.26.74:8080/qingjia/login.do")
                        .addParams("studentName",studentName)
                        .addParams("qingjianeirong",massage)
                        .addParams("isor","0")
                        .addParams("classid",classnName)
                        .addParams("teacherID",teacher)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(Qingjia.this, "CHENGGONG", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void initView() {
        SharedPreferences preferences=getSharedPreferences("data2014",MODE_PRIVATE);
        et_teacher= (EditText) findViewById(R.id.et_teacher);
        et_classnName= (EditText) findViewById(R.id.et_classnName);
        et_massage= (EditText) findViewById(R.id.et_massage);
        tijiao= (Button) findViewById(R.id.shangchuan);
        xue= (TextView) findViewById(R.id.xuehao);
        xue.setText(preferences.getString("account",""));
    }
}
