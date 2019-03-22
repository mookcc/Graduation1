package com.graduation.one.graduation.acyivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.utils.MD5Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 登录
 * */
public class MainActivity extends AppCompatActivity {
    private EditText et_account;
    private EditText et_pass;
    private Button login;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        et_account= (EditText) findViewById(R.id.et_account);
        et_pass= (EditText) findViewById(R.id.et_password);
        login= (Button) findViewById(R.id.login);
        login();
    }
    //登录前检查
    private void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLogin = true;
                String account = et_account.getText().toString();
                String password = et_pass.getText().toString();
                if (account.equals("")) {
                    isLogin = false;
                    Toast.makeText(MainActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                }
                if (password.equals("")) {
                    isLogin = false;
                    Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }
                if (isLogin) {
                    showProgressDialog();
                    checkLogin(account, password);
                }
            }
        });

    }

    //登录接口访问
    private void checkLogin( String account,  String password) {
        final String account1=account;
        final String password1= MD5Util.strToMD5(password);
        OkHttpUtils
                .get()
                .url("http://140.143.26.74:8080/we/login.do")
                .addParams("pass",password1)
                .addParams("number",account1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {

                        SharedPreferences.Editor editor=getSharedPreferences("data2014",MODE_PRIVATE).edit();
                        editor.putString("account",account1).apply();
                        editor.putString("password",password1).apply();
                        getTemporaryJson(response,account1,password1);
                        closeProgressDialog();
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("错误TAG",e.toString());
                        closeProgressDialog();
                    }
                });
    }
//解析JSON
    private void getTemporaryJson(String response, String account,String password) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean sucessed = jsonObject.getBoolean("sucessed");
            s
            if(sucessed){
                JSONObject data = jsonObject.getJSONObject("data");
                //跳转
                enterApp(data.getInt("type"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//跳转+判断
    private void enterApp(int type) {
        if (type==1){
           Intent intent= new Intent(getApplicationContext(),Student1.class);
            startActivity(intent);
        }else if(type==2){
            startActivity(new Intent(getApplicationContext(), Teacher1.class));
        }else{
            Toast.makeText(getApplicationContext(),"账号不存在",Toast.LENGTH_SHORT);
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("登录中");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
