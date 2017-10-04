package com.sc.uiautomatoradapter;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by pyy on 2017/9/19.
 */

public class XMLParser {
    private final String XML  = Main.PATH + Main.XMLName;
    public List<App> apps = null;

    public void init() {
        try {
            File desDir = new File(Main.PATH);
            if (!desDir.exists()) {
                Log.e(AutoTestAdapter.logTag, "No folder existed: " + Main.PATH);
                throw new IOException();
            }

            InputStream is = new FileInputStream(XML);
            SaxActionParser parser = new SaxActionParser();  //创建SaxBookParser实例
            try {
                apps = parser.parse(is);  //解析输入流
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}