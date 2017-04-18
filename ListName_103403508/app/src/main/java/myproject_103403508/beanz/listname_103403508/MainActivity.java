package myproject_103403508.beanz.listname_103403508;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Member> memberList = new ArrayList<>();

    Button btn_add, btn_delete_all, btn_add_example;
    Button btn_sorting_age, btn_sorting_department, btn_display_male;
    Button btn_display_female, btn_display_all;

    TextView tv_total_member;

    ListView lv_member_list;

    memberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);
        btn_add_example = (Button) findViewById(R.id.btn_add_example);
        btn_sorting_age = (Button) findViewById(R.id.btn_sorting_age);
        btn_sorting_department = (Button) findViewById(R.id.btn_sorting_department);
        btn_display_male = (Button) findViewById(R.id.btn_display_male);
        btn_display_female = (Button) findViewById(R.id.btn_display_female);
        btn_display_all = (Button) findViewById(R.id.btn_display_all);
        tv_total_member = (TextView) findViewById(R.id.tv_total_member);
        setTotalMemberTV();

        lv_member_list = (ListView) findViewById(R.id.lv_member_list);

        adapter = new memberAdapter(this);

        lv_member_list.setAdapter(adapter);
        lv_member_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // save the clicked member information to the bundle
                Log.e("onItemClick", "the member is clicked!" + String.valueOf(position));
                Member member = memberList.get(position);
                Intent intent = new Intent();
                intent.setClass(view.getContext(), DisplayMember.class);

                Bundle bundle = new Bundle();
                bundle.putString("name", member.getName());
                bundle.putString("age", member.getAge());
                bundle.putString("sex", member.getSex());
                bundle.putString("department", member.getDepartment());
                bundle.putString("position", String.valueOf(position + 1));

                intent.putExtras(bundle);
                // go the Display member activity
                startActivity(intent);
            }
        });
    }

    private void setTotalMemberTV() {
        int memberNum = memberList.size();
        tv_total_member.setText("共"+ String.valueOf(memberNum) + "筆資料");
    }

    private class memberAdapter extends BaseAdapter{

        // TODO: handle the click event.

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
                // Do not inflate again and again when scrolling the content.
                convertView = layoutInflater.inflate(R.layout.lv_member, parent, false);
            }
            Member member = memberList.get(position);
            TextView tv_name, tv_age, tv_sex, tv_department;
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_age = (TextView) convertView.findViewById(R.id.tv_age);
            tv_sex = (TextView) convertView.findViewById(R.id.tv_sex);
            tv_department = (TextView) convertView.findViewById(R.id.tv_department);

            tv_name.setText(member.getName());
            tv_age.setText(member.getAge());
            tv_sex.setText(member.getSex());
            tv_department.setText(member.getDepartment());

            if (member.getDisplay()) {
                tv_name.setVisibility(View.VISIBLE);
                tv_age.setVisibility(View.VISIBLE);
                tv_sex.setVisibility(View.VISIBLE);
                tv_department.setVisibility(View.VISIBLE);
            } else {
                tv_name.setVisibility(View.GONE);
                tv_age.setVisibility(View.GONE);
                tv_sex.setVisibility(View.GONE);
                tv_department.setVisibility(View.GONE);
            }

            return convertView;
        }

    }

    public void addNewMember(View view) {
        // go to AddNewMember activity
        startActivity(new Intent(this, AddNewMember.class));
    }

    public void deleteAllMember(View view) {
        // delete all members
        memberList = new ArrayList<>();

        setTotalMemberTV();
        adapter.notifyDataSetChanged();
    }

    public void addExample(View view) {
        memberList.add(new Member("張三", "20", "男", "資管"));
        memberList.add(new Member("李四", "22", "男", "資工"));
        memberList.add(new Member("瑪麗", "18", "女", "企管"));
        memberList.add(new Member("甄美麗", "19", "女", "資管"));
        memberList.add(new Member("王六", "17", "男", "資工"));
        memberList.add(new Member("林志玲", "17", "女", "資管"));

        setTotalMemberTV();
        adapter.notifyDataSetChanged();
    }

    public void sortingAge(View view) {
        Log.e("Collection sort", "Collection sort was clicked!");
        Collections.sort(memberList, new Comparator<Member>() {
            @Override
            public int compare(Member member1, Member member2) {
                int age1 = Integer.valueOf(member1.getAge());
                int age2 = Integer.valueOf(member2.getAge());
                return age1 - age2;
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortingDepartment(View view) {
        Collections.sort(memberList, new Comparator<Member>() {
            @Override
            public int compare(Member member1, Member member2) {
                String memberDepart1 = member1.getDepartment();
                String memberDepart2 = member2.getDepartment();
                return memberDepart1.compareTo(memberDepart2);
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void displayMale(View view) {
        for (Member member : memberList) {
            if (member.getSex().equals("女")) {
                member.setDisplay(false);
            } else {
                member.setDisplay(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void displayFemale(View view) {
        for (Member member : memberList) {
            if (member.getSex().equals("男")) {
                member.setDisplay(false);
            } else {
                member.setDisplay(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void displayAll(View view) {
        for (Member member : memberList) {
            // set all the display attributes to true.
            member.setDisplay(true);
        }
        adapter.notifyDataSetChanged();
    }
}
