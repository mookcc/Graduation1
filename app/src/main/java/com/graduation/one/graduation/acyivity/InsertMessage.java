package com.graduation.one.graduation.acyivity;

import android.app.ProgressDialog;
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
 * Created by 666 on 2018/3/13.
 */

public class InsertMessage extends AppCompatActivity {
    private TextView et_myNumber;
    private EditText text_input_prize;
    private Button insertMy;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_prize);
        initView();
    }

    private void initView() {
        SharedPreferences preferences=getSharedPreferences("data2014",MODE_PRIVATE);
        et_myNumber= (TextView) findViewById(R.id.et_student_number);
        text_input_prize= (EditText) findViewById(R.id.et_prize);
        insertMy= (Button) findViewById(R.id.up_to);
        et_myNumber.setText(preferences.getString("account",""));
        insertLog();


    }

    private void insertLog() {

        insertMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean insert = true;
                String myNumber= et_myNumber.getText().toString();
                String prize=text_input_prize.getText().toString();
                if (prize.equals("")){
                    insert=false;
                    Toast.makeText(getApplicationContext(),"奖项不能为空",Toast.LENGTH_SHORT).show();
                }
                if (insert) {
                    showProgressDialog();
                    insert(myNumber, prize);
                }
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("上传中");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    private void insert(String myNumber, String prize) {
        final String myNumber1=myNumber;
        final String prize1=prize;
        OkHttpUtils
                .post()
                .url("http://140.143.26.74:8080/insert_prize/login.do")
                .addParams("studentNumber",myNumber1)
                .addParams("prize",prize1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        closeProgressDialog();
                        //InsertMessage.this.finish();
                        Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
