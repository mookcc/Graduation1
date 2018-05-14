package com.graduation.one.graduation.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_5 extends android.support.v4.app.Fragment {
    private View view;
   private EditText et_student_number1;
    private EditText et_student_name;
    private EditText et_liyou;
    private EditText et_time;
    private Button button;

    public Fragment_5() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_insert_prize1, container, false);
        initView(view);
        initDate();

        return view;
    }

    private void initView(View view) {
        et_liyou=view.findViewById(R.id.et_liyou);
        et_student_name=view.findViewById(R.id.et_student_name);
        et_student_number1=view.findViewById(R.id.et_student_number1);
        et_time=view.findViewById(R.id.et_time);
        button=view.findViewById(R.id.up_to_qingjia);
    }

    private void initDate() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String et_liyou1=et_liyou.getText().toString();
                final String et_student_name1=et_student_name.getText().toString();
                final String et_student_number2=et_student_number1.getText().toString();
                final String et_time1=et_time.getText().toString().trim();
                SharedPreferences editor=getContext().getSharedPreferences("data2014",MODE_PRIVATE);
                OkHttpUtils
                        .post()
                        .url("http://140.143.26.74:8080/insert_qingjia/login.do")
                        .addParams("studentName",et_student_name1)
                        .addParams("qingjianeirong",et_liyou1)
                        .addParams("studentID",et_student_number2)
                        .addParams("teacherID",editor.getString("account",""))
                        .addParams("shijian",et_time1)
                        .addParams("isor","1")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(getContext(),"请假成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}


