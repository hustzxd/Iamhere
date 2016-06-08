package com.example.hustzxd.iamhere.myUtils;

import android.content.Context;
import android.widget.Toast;

import java.util.Random;

/**
 * 工具类
 * Created by buxiaoyao on 2016/6/7.
 */
public class MyUtils {
    /**
     * 生成随机数
     *
     * @param length 随机数的长度
     * @return 返回一个长度为length的随机数
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * toast工具
     *
     * @param context 上下文
     * @param s       需要显示的字符串
     */
    public static void toast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
