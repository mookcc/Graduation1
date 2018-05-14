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

import static com.graduation.one.graduation.MyApplication.getContext;

/**
 * Created by 666 on 2018/5/6.
 */

public class SelectPrize extends AppCompatActivity {
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private ListView listView;

    private TextView textView;
    private MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private SharedPreferences editor;
    private Button chaxun;
    public View view;
    private int a = 0, b = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitystudentcredit);

        initView();
    }

    private void initView() {
        editor = this.getSharedPreferences("data2014", MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.cfenshu);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.item_message0);
        listView = (ListView) findViewById(R.id.item_message100);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        initData(editor);
    }

    private void initData(SharedPreferences editor) {
        mSwipeLayout.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/select_credit/login.do")
                .addParams("studentNumber", editor.getString("account",""))
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

                                if (data.length() == 0) {
                                    Toast.makeText(getContext(), "您暂无成绩", Toast.LENGTH_SHORT).show();
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
                                    mSwipeLayout.setRefreshing(false);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        adapter.notifyDataSetChanged();
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
            SelectPrize.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SelectPrize.this).inflate(R.layout.item_message, parent, false);
                viewHolder = new SelectPrize.ViewHolder();
                viewHolder.tv_name = convertView.findViewById(R.id.list_item_name);
                viewHolder.tv_credit = convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (SelectPrize.ViewHolder) convertView.getTag();
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
