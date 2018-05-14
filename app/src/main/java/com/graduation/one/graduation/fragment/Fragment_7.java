package com.graduation.one.graduation.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_7 extends android.support.v4.app.Fragment {
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
    private View view;

    public Fragment_7() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_message, container, false);
        initView(view);
        initDate();
        return view;
    }

    private void initView(View view) {

        editor = view.getContext().getSharedPreferences("data2015", MODE_PRIVATE);
        name_1 = view.findViewById(R.id.name_1);
        sex_1 = view.findViewById(R.id.sex_1);
        nation = view.findViewById(R.id.nation);
        politicalOutlook = view.findViewById(R.id.politicalOutlook);
        graduatingMiddleSchool = view.findViewById(R.id.graduatingMiddleSchool);
        homeAddres = view.findViewById(R.id.homeAddres);
        dateOfBirth = view.findViewById(R.id.dateOfBirth);
        graduatingClass = view.findViewById(R.id.graduatingClass);
        foreignLanguages1 = view.findViewById(R.id.foreignLanguages1);
    }



    private void initDate() {
        SharedPreferences share =view.getContext().getSharedPreferences("data2014",MODE_PRIVATE);
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
                                    Toast.makeText(getContext(), "您暂无信息", Toast.LENGTH_SHORT).show();
                                } else {
                                    String name = data.getString("examineeNumber");
                                    String sex = data.getString("sex");
                                    String class1 = data.getString("graduatingClass");
                                    SharedPreferences.Editor editor=view.getContext().getSharedPreferences("data2015",MODE_PRIVATE).edit();
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
        name_1.setText("姓名：" + editor.getString("examineeNumber", ""));
        sex_1.setText("性别：" + editor.getString("sex", ""));
        nation.setText("国籍：" + editor.getString("nation", ""));
        politicalOutlook.setText("政治面貌：" + editor.getString("politicalOutlook", ""));
        graduatingMiddleSchool.setText("毕业学校：" + editor.getString("graduatingMiddleSchool", ""));
        graduatingClass.setText("班级：" + editor.getString("graduatingClass", ""));
        homeAddres.setText("住址：" + editor.getString("homeAddress", ""));
        dateOfBirth.setText("出生年月：" + editor.getString("dateOfBirth", ""));
        foreignLanguages1.setText("母语：" + editor.getString("foreignLanguages1", ""));
    }
}


