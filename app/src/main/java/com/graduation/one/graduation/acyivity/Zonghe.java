package com.graduation.one.graduation.acyivity;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.one.graduation.R;
import com.graduation.one.graduation.model.MyClassACT;
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

public class Zonghe extends AppCompatActivity {
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private List<MyClassACT1> myClassACTs1 = new ArrayList<>();
    private ListView listView;
    private ListView listView1;
    private TextView textView;
    private MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private SwipeRefreshLayout mSwipeLayout1;
    private SharedPreferences editor;
    private Button chaxun;
    public View view;
    private int a = 0, b = 0;
    private MyAdapter1 adapter1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_credit);
        initView();
        initDate();
    }
    private void initDate() {
        //mSwipeLayout.setRefreshing(false);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/select_credit/login.do")
                .addParams("studentNumber", editor.getString("classN", ""))
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
                            myClassACTs.clear();
                            if (sucessed) {
                                mSwipeLayout.setRefreshing(true);
                                JSONObject data = jsonObject.getJSONObject("data");

                                if (data.length() == 0) {
                                    Toast.makeText(Zonghe.this, "您暂无成绩", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("TAG", "changdu:" + data.length());
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject mycClass = data.getJSONObject(j + "");
                                        String lClass = mycClass.getString("credit");
                                        String className = mycClass.getString("className");
                                        MyClassACT myClassACT = new MyClassACT();
                                        myClassACT.setClassID(className);
                                        myClassACT.setClassName(lClass);
                                        myClassACTs.add(myClassACT);
                                        a = mycClass.getInt("credit") + a;
                                    }
                                    a=a/data.length();
                                }
                                mSwipeLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        mSwipeLayout1.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/select_prize/login.do")
                .addParams("teacherID", editor.getString("classN", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

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
                                    Toast.makeText(Zonghe.this, "您暂无成绩", Toast.LENGTH_SHORT).show();
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
                                    mSwipeLayout1.setRefreshing(false);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        adapter.notifyDataSetChanged();
        textView.setText(a * 0.9 + b * 60 * 0.1+"");


        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double c = a * 0.9 + b * 60 * 0.1;
                textView.setText(editor.getString("account", "")+"的平均学分绩点为："+a+"    创新实践能力加分："+b*60+"    综合成绩绩点为："+c);

                Toast.makeText(Zonghe.this, "" + c, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        editor = this.getSharedPreferences("data123", MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.cfenshu);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.item_message);
        mSwipeLayout1 = (SwipeRefreshLayout) findViewById(R.id.item_message1);
        listView = (ListView) findViewById(R.id.item_message10);
        listView1 = (ListView) findViewById(R.id.item_message11);
        adapter = new Zonghe.MyAdapter();
        adapter1 = new Zonghe.MyAdapter1();
        listView1.setAdapter(adapter1);
        listView.setAdapter(adapter);
        chaxun = (Button) findViewById(R.id.xuefenjidian);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myClassACTs.size();
        }

        @Override
        public MyClassACT getItem(int position) {
            return myClassACTs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Zonghe.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(Zonghe.this).inflate(R.layout.item_message, parent, false);
                viewHolder = new Zonghe.ViewHolder();
                viewHolder.tv_name = convertView.findViewById(R.id.list_item_name);
                viewHolder.tv_credit = convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Zonghe.ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(getItem(position).getClassID());
            viewHolder.tv_credit.setText(getItem(position).getClassName());


            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_credit;
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
            Zonghe.ViewHolder1 viewHolder1;

            if (convertView == null) {
                convertView = LayoutInflater.from(Zonghe.this).inflate(R.layout.item_class, parent, false);
                viewHolder1 = new Zonghe.ViewHolder1();
                viewHolder1.tv_name = convertView.findViewById(R.id.item_class10);
                //viewHolder1.tv_credit = convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder1);
            } else {
                viewHolder1 = (Zonghe.ViewHolder1) convertView.getTag();
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
