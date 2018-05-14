package com.graduation.one.graduation.acyivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by 666 on 2018/3/13.
 */

public class MyMessage1 extends AppCompatActivity {
    TextView tv_name;
    TextView tv_mySex;
    TextView tv_myOnClass;
    TextView chengjiguanli;
    TextView jiangxiangguanli;
    TextView qingxiaojia;
    TextView message_d;
    TextView zonghe;
    private String CODE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message1);


        initView();
        initData();
    }

    private void initData() {
        chengjiguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyMessage1.this, SelectPrize1.class);
                intent.putExtra("code2", CODE);
                startActivity(intent);
            }
        });
        jiangxiangguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyMessage1.this, SelectCredit1.class);
                intent1.putExtra("code3", CODE);
                startActivity(intent1);
            }
        });
        qingxiaojia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MyMessage1.this, qingjiachaxun.class);
                intent2.putExtra("code4", CODE);
                startActivity(intent2);
            }
        });
        message_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MyMessage1.this, SelectMassage1.class);
                intent3.putExtra("code5", CODE);
                startActivity(intent3);
            }
        });
        zonghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MyMessage1.this, Zonghe.class);
                intent2.putExtra("code4", CODE);
                startActivity(intent2);
            }
        });
    }

    private void initView() {
        SharedPreferences share = getSharedPreferences("data123", MODE_PRIVATE);
        tv_name = (TextView) findViewById(R.id.myName1);
        tv_mySex = (TextView) findViewById(R.id.mySex1);
        tv_myOnClass = (TextView) findViewById(R.id.myOnClass1);
        chengjiguanli = (TextView) findViewById(R.id.chengjiguanli1);
        jiangxiangguanli = (TextView) findViewById(R.id.jiangxiangguanli1);
        qingxiaojia = (TextView) findViewById(R.id.qingxiaojia1);
        message_d = (TextView) findViewById(R.id.message_d1);
        zonghe = (TextView) findViewById(R.id.zonghe);
        initroot(share);
    }

    //提交网络请求
    private void initroot(SharedPreferences share) {
        OkHttpUtils
                .post()
                .url("http://140.143.26.74:8080/information/login.do")
                .addParams("number", share.getString("classN", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean sucessed = jsonObject.getBoolean("flag");

                            if (sucessed) {
                                JSONObject data = jsonObject.getJSONObject("data");

                                if (data.length() == 0) {
                                    Toast.makeText(getApplicationContext(), "您暂无信息", Toast.LENGTH_SHORT).show();
                                } else {
                                    String name = data.getString("examineeNumber");
                                    String sex = data.getString("sex");
                                    String class1 = data.getString("graduatingClass");
                                    tv_myOnClass.setText(class1);
                                    tv_mySex.setText(sex);
                                    tv_name.setText(name);
                                    SharedPreferences.Editor editor = getSharedPreferences("data456", MODE_PRIVATE).edit();
                                    editor.putString("examineeNumber", data.getString("examineeNumber")).apply();
                                    editor.putString("sex", data.getString("sex")).apply();
                                    editor.putString("nation", data.getString("nation")).apply();
                                    editor.putString("politicalOutlook", data.getString("politicalOutlook")).apply();
                                    editor.putString("graduatingMiddleSchool", data.getString("graduatingMiddleSchool")).apply();
                                    editor.putString("homeAddress", data.getString("homeAddress")).apply();
                                    editor.putString("dateOfBirth", data.getString("dateOfBirth")).apply();
                                    editor.putString("graduatingClass", data.getString("graduatingClass")).apply();
                                    editor.putString("foreignLanguages1", data.getString("foreignLanguages1")).apply();
                                    editor.putString("studentNumber", data.getString("studentNumber")).apply();

                                    CODE = data.getString("studentNumber");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });
    }


}

