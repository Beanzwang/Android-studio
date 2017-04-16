package com.example.mid_term_practice;

/**
 * Created by wangbeanz on 11/04/2017.
 */

public class Member {

    private String name, age, department, sex;

    public Member(String name, String age, String sex, String department) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getSex() {
        return sex;
    }

}
