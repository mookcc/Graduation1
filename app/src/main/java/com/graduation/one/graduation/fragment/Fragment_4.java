package com.graduation.one.graduation.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 666 on 2018/4/25.
 */

public class Fragment_4 extends android.support.v4.app.Fragment {
    private View view;
    private List<MyClassACT> myClassACTs = new ArrayList<>();
    private ListView listView;
    private Fragment_4.MyAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private SharedPreferences editor;

    public Fragment_4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.qingxiaojia, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        mSwipeLayout.setRefreshing(true);
        OkHttpUtils.post()
                .url("http://140.143.26.74:8080/select_prize_t/login.do")
                .addParams("teacherID", editor.getString("account", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(), "shibai" + editor.getString("account", ""), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean sucessed = jsonObject.getBoolean("sucessed");
                            myClassACTs.clear();
                            if (sucessed) {
                                JSONObject data = jsonObject.getJSONObject("data");

                                if (data.length() == 0) {
                                    Toast.makeText(getContext(), "您暂无需审核", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("TAG", "changdu:" + data.length());
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject mycClass = data.getJSONObject(j + "");
                                        String lClass = mycClass.getString("prize");
                                        String className = mycClass.getString("zhengming");
                                        int isor = mycClass.getInt("isor");
                                        if (isor == 0) {
                                            MyClassACT myClassACT = new MyClassACT();
                                            myClassACT.setClassID(className);
                                            myClassACT.setClassName(lClass);
                                            myClassACTs.add(myClassACT);
                                        }
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
    }

    private void initView(View view) {
        editor = getContext().getSharedPreferences("data2014", MODE_PRIVATE);
        mSwipeLayout = view.findViewById(R.id.item_qing);
        listView = view.findViewById(R.id.item_qingjia);
        adapter = new Fragment_4.MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClassACT myClassACT=myClassACTs.get(position);
                final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("要通过吗？");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),"nin",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

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
            Fragment_4.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
                viewHolder = new Fragment_4.ViewHolder();
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.list_item_name);
                viewHolder.tv_credit = (TextView) convertView.findViewById(R.id.list_item_message);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Fragment_4.ViewHolder) convertView.getTag();
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



