package com.graduation.one.graduation.acyivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.graduation.one.graduation.R;

/**
 * Created by 666 on 2018/5/6.
 */

public class SelectMassage extends AppCompatActivity {
    private TextView name_1;
    private TextView sex_1;
    private TextView nation;
    private TextView politicalOutlook;
    private TextView graduatingMiddleSchool;
    private TextView homeAddres;
    private TextView dateOfBirth;
    private TextView graduatingClass;
    private TextView foreignLanguages1;
    private SharedPreferences editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_message);
        initView();
        initData();
    }

    private void initData() {
        name_1.setText("姓名："+editor.getString("examineeNumber", ""));
        sex_1.setText("性别："+editor.getString("sex", ""));
        nation.setText("国籍："+editor.getString("nation", ""));
        politicalOutlook.setText("政治面貌："+editor.getString("politicalOutlook", ""));
        graduatingMiddleSchool.setText("毕业学校："+editor.getString("graduatingMiddleSchool", ""));
        graduatingClass.setText("班级："+editor.getString("graduatingClass", ""));
        homeAddres.setText("住址："+editor.getString("homeAddress", ""));
        dateOfBirth.setText("出生年月："+editor.getString("dateOfBirth", ""));
        foreignLanguages1.setText("母语："+editor.getString("foreignLanguages1", ""));
//        OkHttpUtils
//                .post()
//                .url("http://140.143.26.74:8080/information/login.do")
//                .addParams("number", editor.getString("account", ""))
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            boolean sucessed = jsonObject.getBoolean("flag");
//
//                            if (sucessed) {
//                                JSONObject data = jsonObject.getJSONObject("data");
//
//                                if (data.length() == 0) {
//                                    Toast.makeText(getApplicationContext(), "您暂无信息", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    String examineeNumber = data.getString("examineeNumber");
//                                    String sex_2 = data.getString("sex");
//                                    String nation1 = data.getString("nation");
//                                    String politicalOutlook1 = data.getString("politicalOutlook");
//                                    String graduatingMiddleSchool1 = data.getString("graduatingMiddleSchool");
//                                    String homeAddres1= data.getString("homeAddres");
//                                    String dateOfBirth1 = data.getString("dateOfBirth");
//                                    String graduatingClass1 = data.getString("graduatingClass");
//                                    String foreignLanguages11 = data.getString("foreignLanguages1");
//
//                                    Toast.makeText(SelectMassage.this,"123",Toast.LENGTH_SHORT).show();
//
//
//                                }
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                });

    }

    private void initView() {
        editor = getSharedPreferences("data2015", MODE_PRIVATE);
        name_1 = (TextView) findViewById(R.id.name_1);
        sex_1 = (TextView) findViewById(R.id.sex_1);
        nation = (TextView) findViewById(R.id.nation);
        politicalOutlook = (TextView) findViewById(R.id.politicalOutlook);
        graduatingMiddleSchool = (TextView) findViewById(R.id.graduatingMiddleSchool);
        homeAddres = (TextView) findViewById(R.id.homeAddres);
        dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        graduatingClass = (TextView) findViewById(R.id.graduatingClass);
        foreignLanguages1 = (TextView) findViewById(R.id.foreignLanguages1);
    }
}
