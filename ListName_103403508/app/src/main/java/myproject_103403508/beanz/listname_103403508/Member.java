package myproject_103403508.beanz.listname_103403508;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by wangbeanz on 18/04/2017.
 */

public class Member {

    String name;
    String age;
    String sex;
    String department;
    boolean display;

    public Member(String name, String age, String sex, String department) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.department = department;
        display = true;
    }

    void setDisplay(boolean display) {
        this.display = display;
    }

    boolean getDisplay() {
        return display;
    }

    String getName(){
        return name;
    }

    String getAge() {
        return age;
    }

    String getSex() {
        return sex;
    }

    String getDepartment() {
        return department;
    }


}
