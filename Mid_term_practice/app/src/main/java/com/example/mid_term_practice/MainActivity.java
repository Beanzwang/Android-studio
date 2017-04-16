package com.example.mid_term_practice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv_member;
    public static List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        lv_member = (ListView) findViewById(R.id.lv_member);
        lv_member.setAdapter(new memberAdapter(this));
        if (memberList.isEmpty()) {
            memberList.add(new Member("張三", "20", "男", "資管"));
            memberList.add(new Member("李四", "22", "男", "資工"));
            memberList.add(new Member("瑪麗", "18", "女", "企管"));
        }
    }

    public void click_add_member(View view) {
        startActivity(new Intent(this, Add_Member.class));
    }

    private class memberAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public memberAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return memberList.size();
        }

        @Override
        public Object getItem(int position) {
            return memberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listview_item, parent,false);
            }
            Member member = memberList.get(position);
            TextView tv_name, tv_age, tv_sex, tv_department;
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_name.setText(member.getName());
            tv_age = (TextView) convertView.findViewById(R.id.tv_age);
            tv_age.setText(member.getAge());
            tv_sex = (TextView) convertView.findViewById(R.id.tv_sex);
            tv_sex.setText(member.getSex());
            tv_department = (TextView) convertView.findViewById(R.id.tv_department);
            tv_department.setText(member.getDepartment());
            return convertView;
        }
    }



}
