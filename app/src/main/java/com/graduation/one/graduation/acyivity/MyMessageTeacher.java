package com.graduation.one.graduation.acyivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by 666 on 2018/5/6.
 */

public class MyMessageTeacher extends AppCompatActivity {
    private TextView teacherName;
    private TextView id1;
    private TextView teacherSex;
    private TextView teacherPhone;
    private TextView teacherEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_teacher);
        initView();
        iniDate();
        itOnclik();
    }

    private void itOnclik() {
        teacherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyMessageTeacher.this,ChangePhone.class);
                startActivity(intent);
            }
        });
        teacherEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyMessageTeacher.this,ChangeEmail.class);
                startActivity(intent);
            }
        });
    }

    private void iniDate() {
         SharedPreferences editor=getSharedPreferences("data2014", MODE_PRIVATE);
        OkHttpUtils
                .post()
                .url("http://140.143.26.74:8080/select_teacher/login.do")
                .addParams("studentNumber",editor.getString("account",""))
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
                            if (sucessed){
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONObject data1 = data.getJSONObject("0");
                                String teachID=data1.getString("teachID");
                                String phone=data1.getString("phone");
                                String sex=data1.getString("sex");
                                String name=data1.getString("name");
                                String email=data1.getString("email");
                                teacherEmail.setText("邮箱："+email);
                                teacherName.setText("姓名："+name);
                                teacherPhone.setText("手机号码："+phone);
                                teacherSex.setText("性别："+sex);
                                id1.setText(teachID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        teacherEmail= (TextView) findViewById(R.id.teacherEmail);
        teacherName= (TextView) findViewById(R.id.teacherName);
        teacherPhone= (TextView) findViewById(R.id.teacherPhone);
        teacherSex= (TextView) findViewById(R.id.teacherSex);
        id1= (TextView) findViewById(R.id.id_teacher1);
    }

}
