package com.example.hustzxd.iamhere.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/6/4.
 */
public class MyUser extends BmobUser {
    private String stuNo;
    private String stuName;
    private String personalizedSignature;
    private BmobFile userPic;

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public BmobFile getUserPic() {
        return userPic;
    }

    public void setUserPic(BmobFile userPic) {
        this.userPic = userPic;
    }
}
