package com.example.hustzxd.iamhere.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 签到记录
 * Created by buxiaoyao on 2016/6/7.
 */
public class Checkins extends BmobObject {

    private String stuObjectId;

    private String randomCode;

    private String courseName;

    private String stuNumber;

    private String stuName;

    public String getStuObjectId() {
        return stuObjectId;
    }

    public void setStuObjectId(String stuObjectId) {
        this.stuObjectId = stuObjectId;
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

    @Override
    public String toString() {
        return "Checkins{" +
                "randomCode='" + randomCode + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuNumber='" + stuNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
