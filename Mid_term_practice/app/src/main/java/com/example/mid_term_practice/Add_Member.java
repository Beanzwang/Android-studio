package com.example.mid_term_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_Member extends AppCompatActivity {

    EditText et_name, et_age, et_sex, et_department;
    Button btn_add;
    String name, age, sex, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__member);

        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_department = (EditText) findViewById(R.id.et_department);
        btn_add = (Button) findViewById(R.id.btn_add);
    }

    private boolean checkName(String name) {
        if(name.length() <= 4) {
            this.name = name;
            return true;
        } else {
            Toast.makeText(this, "Name length must be under four words.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkAge(String age) {
        if (age.length() <= 3) {
            // check regex
            String pattern = "^[1-9][0-9]*";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(age);
            if (m.find()) {
                this.age = age;
                return true;
            } else {
                Toast.makeText(this, "Invalid age.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Age must be under hundred.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkSex(String sex) {
        if (sex.equals("男") || sex.equals("女")) {
            this.sex = sex;
            return true;
        } else {
            Toast.makeText(this, "Invalid sex.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkDepartment(String department) {
        if (department.length() <= 6) {
            this.department = department;
            return true;
        } else {
            Toast.makeText(this, "Department length must be under 6 words.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void click_add_member(View view) {
        boolean valid;
        valid = checkName(et_name.getText().toString()) && checkAge(et_age.getText().toString())
                && checkSex(et_sex.getText().toString()) && checkDepartment(et_department.getText().toString());
        if (valid) {
            // create bundle and go to Fin_Add_Member
            Intent intent = new Intent();
            intent.setClass(this, Fin_Add_Member.class);

            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("age", age);
            bundle.putString("sex", sex);
            bundle.putString("department", department);

            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            // do nothing
        }
    }
}
