package com.graduation.one.graduation.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class Fragment_1 extends android.support.v4.app.Fragment {
    private View view;
    private EditText et_teacher;
    private EditText et_prize;
    private EditText et_massage;
    private Button tijiao;
    private TextView xue;
    private SharedPreferences editor;

    public Fragment_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.qingjia, container, false);
        initView(view);
        initDate();
        return view;
    }

    private void initDate() {
        tijiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String xue1=editor.getString("account", "");
                final String teacher=et_teacher.getText().toString().trim();
                final String et_prize1=et_prize.getText().toString().trim();
                final String massage=et_massage.getText().toString().trim();
                OkHttpUtils
                        .get()
                        .url("http://140.143.26.74:8080/insert_prize/login.do")
                        .addParams("studentNumber",xue1)
                        .addParams("isor","0")
                        .addParams("prize",et_prize1)
                        .addParams("teacherID",teacher)
                        .addParams("zhengming",massage)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    boolean s=jsonObject.getBoolean("sucessed");
                                    if (s) {
                                        Toast.makeText(getContext(), "提交成功！", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    private void initView(View view) {
        editor = this.getContext().getSharedPreferences("data2014", MODE_PRIVATE);
        et_teacher = view.findViewById(R.id.et_teacher);
        et_prize = view.findViewById(R.id.et_prize1);
        et_massage = view.findViewById(R.id.et_massage);
        tijiao = view.findViewById(R.id.shangchuan);
        xue = view.findViewById(R.id.xuehao);
        xue.setText(editor.getString("account", ""));
    }

}


