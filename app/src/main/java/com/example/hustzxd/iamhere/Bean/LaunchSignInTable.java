package com.example.hustzxd.iamhere.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 发起签到记录
 * Created by buxiaoyao on 2016/6/7.
 */
public class LaunchSignInTable extends BmobObject {
    private String name;
    private String date;
    private String positive;
    private String courseName;
    private String randomCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    @Override
    public String toString() {
        return "LaunchSignInTable{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", positive='" + positive + '\'' +
                ", courseName='" + courseName + '\'' +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }
}
