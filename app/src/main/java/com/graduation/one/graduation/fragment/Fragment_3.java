package com.graduation.one.graduation.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.acyivity.ChangePswActivity;
import com.graduation.one.graduation.acyivity.MyMessage;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_3 extends android.support.v4.app.Fragment{
    private View view;
    private TextView studentName;
    private TextView student_changePwd;
    private TextView studentMessage;

    public Fragment_3() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // 代替这个界面
            view = inflater.inflate(R.layout.activity_id_student, container, false);
            initView(view);
            return view;
        }

    private void initView(View view) {
        SharedPreferences share=this.getContext().getSharedPreferences("data2014",MODE_PRIVATE);
        student_changePwd=view.findViewById(R.id.student_changePwd);
        studentMessage=view.findViewById(R.id.studentMessage);
        studentName=view.findViewById(R.id.studentName);
        studentName.setText(share.getString("account",""));
        ininData();
    }

    private void ininData() {

        studentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MyMessage .class);
                startActivity(intent);
            }
        });
        student_changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChangePswActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void initroot(SharedPreferences share) {
//        OkHttpUtils
//                .post()
//                .url("http://140.143.26.74:8080/information/login.do")
//                .addParams("number",share.getString("account",""))
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
//                            if (sucessed){
//                                JSONObject data=jsonObject.getJSONObject("data");
//
//                                if (data.length() == 0){
//                                    Toast.makeText(getContext(),"您暂无信息",Toast.LENGTH_SHORT).show();
//                                }else {
//                                    String name=data.getString("examineeNumber");
//                                    String sex=data.getString("sex");
//                                    String class1=data.getString("scienceClass");
//                                    tv_myOnClass.setText(class1);
//                                    tv_mySex.setText(sex);
//                                    tv_name.setText(name);
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
//    }

}


