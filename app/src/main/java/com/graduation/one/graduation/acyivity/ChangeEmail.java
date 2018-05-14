package com.graduation.one.graduation.acyivity;

import android.content.SharedPreferences;
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
 * Created by 666 on 2018/5/7.
 */

public class ChangeEmail extends AppCompatActivity {
    private EditText et_email1;
    private EditText et_email;
    private Button email_change;

    public ChangeEmail() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        initView();
        initDate();
    }

    private void initView() {
        et_email= (EditText) findViewById(R.id.et_email);
        et_email1= (EditText) findViewById(R.id.et_email_true);
        email_change = (Button) findViewById(R.id.email_change);
    }

    private void initDate() {

        email_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1=et_email1.getText().toString();
                String email2=et_email.getText().toString();
                if(email1==email2) {
                    final String email=et_email1.getText().toString();
                    final SharedPreferences editor=getSharedPreferences("data2014",MODE_PRIVATE);
                    OkHttpUtils
                            .post()
                            .url("http://140.143.26.74:8080/changeEmail/login.do")
                            .addParams("number", editor.getString("account", ""))
                            .addParams("pass", email)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(ChangeEmail.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {

                                    Toast.makeText(ChangeEmail.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(ChangeEmail.this, "修改失败，两次输入内容不同！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
