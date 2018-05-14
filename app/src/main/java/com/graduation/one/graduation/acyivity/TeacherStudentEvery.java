package com.graduation.one.graduation.acyivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
 * Created by 666 on 2018/4/14.
 *
 * 通过班级获取学生姓名
 */

public class TeacherStudentEvery extends AppCompatActivity {
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private ListView listView;
    private MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private SharedPreferences editor;
private String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every);
        editor = getSharedPreferences("data2014",MODE_PRIVATE);
        Intent intent=getIntent();
        code=intent.getStringExtra("code1");
        initView();

        initDate();
    }

    private void initDate() {
        mSwipeLayout.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/selectStudent/login.do")
               // .addParams("teachTD", editor.getString("account", ""))
                .addParams("classID", editor.getString("classN1",""))
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
                                JSONObject data = jsonObject.getJSONObject("data");

                                for (int j = 0; j <=data.length(); j++) {
                                    JSONObject data1 = data.getJSONObject(j+"");
                                    final String SName = data1.getString("name");
                                    String Sid = data1.getString("studentNumber");
                                    MyClassACT myClassACT=new MyClassACT();
                                    myClassACT.setStudentName(SName);
                                    myClassACT.setStudentID(Sid);
                                    myClassACTs.add(myClassACT);
                                    mSwipeLayout.setRefreshing(false);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.everyOne);
        listView = (ListView) findViewById(R.id.item_everyName);
        adapter = new TeacherStudentEvery.MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClassACT myClassACT = myClassACTs.get(position);
                Toast.makeText(TeacherStudentEvery.this,myClassACT.getClassName(),Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor sharedPreferences = getSharedPreferences("data123", MODE_PRIVATE).edit();
                sharedPreferences.putString("classN", myClassACT.getStudentID()).apply();
                if(code=="0"){

                }else{
                    Intent intent=new Intent(TeacherStudentEvery.this,MyMessage1.class);

                    startActivity(intent);
                }


            }
        });


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
            TeacherStudentEvery.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(TeacherStudentEvery.this).inflate(R.layout.item_class, parent, false);
                viewHolder = new TeacherStudentEvery.ViewHolder();
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.item_class10);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (TeacherStudentEvery.ViewHolder) convertView.getTag();
            }

           viewHolder.tv_name.setText(getItem(position).getStudentName());

            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name;
    }
}
