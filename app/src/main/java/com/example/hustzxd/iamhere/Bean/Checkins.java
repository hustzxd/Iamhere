package com.example.hustzxd.iamhere.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 签到记录
 * Created by buxiaoyao on 2016/6/7.
 */
public class Checkins extends BmobObject {
    private String randomCode;
    private String stuName;
    private String stuNumber;

    private String date;

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Checkins{" +
                "randomCode='" + randomCode + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuNumber='" + stuNumber + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
