package com.example.mid_term_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.mid_term_practice.MainActivity.memberList;

public class Fin_Add_Member extends AppCompatActivity {

    TextView tv_name, tv_age, tv_sex, tv_department;
    String name, age, sex, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin__add__member);

        // get bundle from add_member class
        Bundle bundle = getIntent().getExtras();
        this.name = bundle.getString("name");
        this.age = bundle.getString("age");
        this.sex = bundle.getString("sex");
        this.department = bundle.getString("department");
        memberList.add(new Member(name, age, sex, department));

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_department = (TextView) findViewById(R.id.tv_department);

        settingText();
    }

    private void settingText() {
        tv_name.setText(tv_name.getText().toString().concat(name));
        tv_age.setText(tv_age.getText().toString().concat(age));
        tv_sex.setText(tv_sex.getText().toString().concat(sex));
        tv_department.setText(tv_department.getText().toString().concat(department));
    }


    public void click_back_main(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
