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
import com.graduation.one.graduation.acyivity.MyClass;
import com.graduation.one.graduation.acyivity.MyMessageTeacher;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_6 extends android.support.v4.app.Fragment {
    private View view;
    private TextView teacherMessage;
    private TextView teacher_class;
    private TextView teacher_changePwd;
    private TextView id_teacher;

    public Fragment_6() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.id_teacher, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        SharedPreferences share=this.getContext().getSharedPreferences("data2014",MODE_PRIVATE);
        teacher_changePwd=view.findViewById(R.id.teacher_changePwd);
        teacher_class=view.findViewById(R.id.teacher_class);
        teacherMessage=view.findViewById(R.id.teacherMessage);
        id_teacher=view.findViewById(R.id.id_teacher);
        id_teacher.setText(share.getString("account",""));
        teacher_changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChangePswActivity.class);
                startActivity(intent);
            }
        });
        teacher_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MyClass.class);
                startActivity(intent);
            }
        });
        teacherMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MyMessageTeacher.class);
                startActivity(intent);
            }
        });

    }


}


