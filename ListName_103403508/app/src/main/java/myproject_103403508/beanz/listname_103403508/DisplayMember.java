package myproject_103403508.beanz.listname_103403508;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static myproject_103403508.beanz.listname_103403508.MainActivity.memberList;

public class DisplayMember extends AppCompatActivity {

    TextView tv_name, tv_age, tv_sex, tv_department, tv_member_position;
    String name, age, sex, department, position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_member);

        Bundle bundle = getIntent().getExtras();
        this.name = bundle.getString("name");
        this.age = bundle.getString("age");
        this.sex = bundle.getString("sex");
        this.department = bundle.getString("department");
        this.position = bundle.getString("position");
        init();
    }

    private void init() {
        tv_member_position = (TextView) findViewById(R.id.tv_member_position);
        tv_member_position.setText(position + "/" + String.valueOf(memberList.size()));
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_department = (TextView) findViewById(R.id.tv_department);

        tv_name.setText(tv_name.getText().toString().concat(name));
        tv_age.setText(tv_age.getText().toString().concat(age));
        tv_sex.setText(tv_sex.getText().toString().concat(sex));
        tv_department.setText(tv_department.getText().toString().concat(department));
    }

    public void getFirstMember(View view) {
        // get the first member in the memberList.
        Member member = memberList.get(0);
        tv_member_position.setText("1/" + String.valueOf(memberList.size()));
        tv_name.setText("姓名：" + member.getName());
        tv_age.setText("年齡：" + member.getAge());
        tv_sex.setText("性別：" + member.getSex());
        tv_department.setText("系別：" + member.getDepartment());
    }

    public void goToMain(View view) {
        // go back to mainActivity.
        startActivity(new Intent(this, MainActivity.class));
    }

    public void getLastMember(View view) {
        // get the last member in the memberList.
        tv_member_position.setText(String.valueOf(memberList.size()) + "/" + String.valueOf(memberList.size()));
        Member member = memberList.get(memberList.size() - 1);
        tv_name.setText("姓名：" + member.getName());
        tv_age.setText("年齡：" + member.getAge());
        tv_sex.setText("性別：" + member.getSex());
        tv_department.setText("系別：" + member.getDepartment());
    }
}
