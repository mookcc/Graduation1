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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 666 on 2018/3/13.
 */

public class SelectAchievement extends AppCompatActivity {
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private ListView listView;
    private MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private SharedPreferences editor;
    private Button chaxun;
    private int a=0,b=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_credit);
        initView();
        initDate();

    }

    private void initDate() {
        mSwipeLayout.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/select_credit/login.do")
                .addParams("studentNumber",editor.getString("account",""))
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
                            if (sucessed){
                                JSONObject data=jsonObject.getJSONObject("data");

                                if (data.length() == 0){
                                   Toast.makeText(getApplicationContext(),"您暂无成绩",Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.d("TAG","changdu:"+ data.length());
                                    for (int j = 0; j < data.length(); j++){
                                        JSONObject mycClass=data.getJSONObject(j+"");
                                        String lClass=mycClass.getString("className");
                                        String className=mycClass.getString("classID");
                                        MyClassACT myClassACT=new MyClassACT();
                                        myClassACT.setClassID(className);
                                        myClassACT.setClassName(lClass);
                                        myClassACTs.add(myClassACT);
                                        a=mycClass.getInt("className")+a;
                                    }
                                }
                                mSwipeLayout.setRefreshing(false);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post()
                        .url("http://140.143.26.74:8080/select_prize/login.do")
                        .addParams("teacherID",editor.getString("account",""))
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
                                    boolean sucessed = jsonObject.getBoolean("flag");
                                    myClassACTs.clear();
                                    if (sucessed) {
                                        JSONObject data = jsonObject.getJSONObject("data");

                                        for (int j=0; j<=data.length(); j++){
                                            b+=b;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                double c= a*0.9+b*60*0.1;
                Toast.makeText(SelectAchievement.this,""+c,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        editor = getSharedPreferences("data2014",MODE_PRIVATE);
        mSwipeLayout=(SwipeRefreshLayout)findViewById(R.id.item_message);
        listView= (ListView) findViewById(R.id.item_message10);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        chaxun= (Button) findViewById(R.id.xuefenjidian);
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SelectAchievement.this).inflate(R.layout.item_message, parent, false);
                viewHolder = new SelectAchievement.ViewHolder();
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.list_item_name);
                viewHolder.tv_credit = (TextView) convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (SelectAchievement.ViewHolder) convertView.getTag();
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
}
