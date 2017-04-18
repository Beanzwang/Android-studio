package myproject_103403508.beanz.listname_103403508;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static myproject_103403508.beanz.listname_103403508.MainActivity.memberList;

public class AddNewMember extends AppCompatActivity {

    TextView tv_name, tv_age, tv_sex, tv_department;
    EditText et_name, et_age, et_sex, et_department;
    Button btn_clear_columns, btn_go_to_main, btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);
        init();
    }

    private void init() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_department = (TextView) findViewById(R.id.tv_department);

        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_department = (EditText) findViewById(R.id.et_department);
        et_name.setText("");
        et_age.setText("");
        et_sex.setText("");
        et_department.setText("");

        btn_clear_columns = (Button) findViewById(R.id.btn_clear_columns);
        btn_go_to_main = (Button) findViewById(R.id.btn_go_to_main);
        btn_add = (Button) findViewById(R.id.btn_confirm_add_member);
    }

    public void clearColumns(View view) {
        // clear all the editText columns
        et_name.setText("");
        et_age.setText("");
        et_sex.setText("");
        et_department.setText("");
    }

    public void goToMain(View view) {
        // Go to MainActivity.
        startActivity(new Intent(this, MainActivity.class));
    }

    public void confirmAddMember(View view) {
        boolean columnNotEmpty;

        // check if any of the column is empty, if yes, show toast warning messages.
        if (et_name.getText().toString().equals("") || et_age.getText().toString().equals("") ||
                et_sex.getText().toString().equals("") || et_department.getText().toString().equals("")) {
            Toast.makeText(this, "Columns cannot be empty.", Toast.LENGTH_SHORT).show();
            columnNotEmpty = false;
        } else {
            columnNotEmpty = true;
        }

        if (columnNotEmpty) {
            if (checkName(et_name.getText().toString()) && checkAge(et_age.getText().toString())
                    && checkSex(et_sex.getText().toString()) && checkDepartment(et_department.getText().toString())) {
                memberList.add(new Member(et_name.getText().toString(), et_age.getText().toString(),
                        et_sex.getText().toString(), et_department.getText().toString()));
                Toast.makeText(this, "Successfully add new member!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkName(String name) {
        if(name.length() <= 4) {
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
            return true;
        } else {
            Toast.makeText(this, "Invalid sex.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkDepartment(String department) {
        if (department.length() <= 6) {
            return true;
        } else {
            Toast.makeText(this, "Department length must be under 6 words.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
