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

public class ChangePhone extends AppCompatActivity {
    private EditText et_phone1;
    private EditText et_phone;
    private Button phone_change;

    public ChangePhone() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        initView();
        initDate();
    }

    private void initView() {
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_phone1= (EditText) findViewById(R.id.et_phone_true);
        phone_change= (Button) findViewById(R.id.phone_change);
    }

    private void initDate() {

        phone_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone1=et_phone1.getText().toString();
                String phone2=et_phone.getText().toString();
                if(phone1==phone2) {
                    final String phone=et_phone1.getText().toString();
                    final SharedPreferences editor=getSharedPreferences("data2014",MODE_PRIVATE);
                    OkHttpUtils
                            .post()
                            .url("http://140.143.26.74:8080/changePhone/login.do")
                            .addParams("number", editor.getString("account", ""))
                            .addParams("pass", phone)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(ChangePhone.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {

                                    Toast.makeText(ChangePhone.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(ChangePhone.this, "修改失败，两次输入内容不同！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
