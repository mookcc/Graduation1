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

public class MyMessage extends AppCompatActivity {
    TextView tv_name;
    TextView tv_mySex;
    TextView tv_myOnClass;
    TextView chengjiguanli;
    TextView jiangxiangguanli;
    TextView message_d;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initView();
        initData();
    }

    private void initData() {
        chengjiguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyMessage.this, SelectCredit .class);
                startActivity(intent);
            }
        });
        jiangxiangguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyMessage.this, SelectPrize.class);
                startActivity(intent1);
            }
        });
        message_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(MyMessage.this, SelectMassage.class);
                startActivity(intent3);
            }
        });
    }

    private void initView() {
        SharedPreferences share = getSharedPreferences("data2014", MODE_PRIVATE);
        tv_name = (TextView) findViewById(R.id.myName);
        tv_mySex = (TextView) findViewById(R.id.mySex);
        tv_myOnClass = (TextView) findViewById(R.id.myOnClass);
        chengjiguanli = (TextView) findViewById(R.id.chengjiguanli);
        jiangxiangguanli = (TextView) findViewById(R.id.jiangxiangguanli);
        message_d = (TextView) findViewById(R.id.message_d);
        initroot(share);
    }

    //提交网络请求
    private void initroot(SharedPreferences share) {
        OkHttpUtils
                .post()
                .url("http://140.143.26.74:8080/information/login.do")
                .addParams("number", share.getString("account", ""))
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
                                    SharedPreferences.Editor editor=getSharedPreferences("data2015",MODE_PRIVATE).edit();
                                    editor.putString("examineeNumber",data.getString("examineeNumber")).apply();
                                    editor.putString("password",data.getString("examineeNumber")).apply();
                                    editor.putString("sex",data.getString("sex")).apply();
                                    editor.putString("nation",data.getString("nation")).apply();
                                    editor.putString("politicalOutlook",data.getString("politicalOutlook")).apply();
                                    editor.putString("graduatingMiddleSchool",data.getString("graduatingMiddleSchool")).apply();
                                    editor.putString("homeAddress",data.getString("homeAddress")).apply();
                                    editor.putString("dateOfBirth",data.getString("dateOfBirth")).apply();
                                    editor.putString("graduatingClass",data.getString("graduatingClass")).apply();
                                    editor.putString("foreignLanguages1",data.getString("foreignLanguages1")).apply();


                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });
    }


    }
