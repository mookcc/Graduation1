package com.graduation.one.graduation.acyivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.model.MyClassACT1;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 666 on 2018/5/6.
 */

public class SelectCredit1 extends AppCompatActivity {
    private SharedPreferences editor;
    private List<MyClassACT1> myClassACTs1 = new ArrayList<>();
    private ListView listView1;
    private TextView textView;

    private SwipeRefreshLayout mSwipeLayout;

    public View view;
    private int a = 0, b = 0;
    private MyAdapter1 adapter1;
    private String code;
    public SelectCredit1() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitystudentcredit1);
        initView();
        Intent intent=getIntent();
        code=intent.getStringExtra("code2");
    }

    private void initData(SharedPreferences editor) {
        mSwipeLayout.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/select_prize/login.do")
                .addParams("teacherID", editor.getString("classN", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(SelectCredit1.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            boolean sucessed = jsonObject.getBoolean("sucessed");
                            myClassACTs1.clear();
                            if (sucessed) {
                                JSONObject data = jsonObject.getJSONObject("data");

                                if (data.length() == 0) {
                                    Toast.makeText(SelectCredit1.this, "您暂无成绩", Toast.LENGTH_SHORT).show();
                                    mSwipeLayout.setRefreshing(false);
                                } else {
                                    Log.d("TAG", "changdu:" + data.length());
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject mycClass1 = data.getJSONObject(j + "");
                                        String mClass = mycClass1.getString("prize");

                                        MyClassACT1 myClassACT = new MyClassACT1();

                                        myClassACT.setStudentName(mClass);
                                        myClassACTs1.add(myClassACT);
                                        b += 1;

                                    }
                                    mSwipeLayout.setRefreshing(false);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
       adapter1.notifyDataSetChanged();
    }

    private void initView() {
        editor =this.getSharedPreferences("data123", MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.cfenshu);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.item_message101);

        listView1 = (ListView) findViewById(R.id.item_message102);

        adapter1 = new SelectCredit1.MyAdapter1();
        listView1.setAdapter(adapter1);
        initData(editor);

    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return myClassACTs1.size();
        }

        @Override
        public MyClassACT1 getItem(int position) {
            return myClassACTs1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectCredit1.ViewHolder1 viewHolder1;

            if (convertView == null) {
                convertView = LayoutInflater.from(SelectCredit1.this).inflate(R.layout.item_class, parent, false);
                viewHolder1 = new SelectCredit1.ViewHolder1();
                viewHolder1.tv_name = convertView.findViewById(R.id.item_class10);
                //viewHolder1.tv_credit = convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder1);
            } else {
                viewHolder1 = (SelectCredit1.ViewHolder1) convertView.getTag();
            }
            viewHolder1.tv_name.setText(getItem(position).getStudentName());

            // viewHolder.tv_credit.setText(getItem(position).getClassName());

            return convertView;
        }
    }

    static class ViewHolder1 {
        TextView tv_name;
        TextView tv_credit;
    }
}
