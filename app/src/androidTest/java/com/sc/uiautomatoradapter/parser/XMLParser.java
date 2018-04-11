package com.sc.uiautomatoradapter.parser;

import android.os.Environment;
import android.util.Log;

import com.sc.uiautomatoradapter.AutoTestAdapter;
import com.sc.uiautomatoradapter.Main;
import com.sc.uiautomatoradapter.app.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by pyy on 2017/9/19.
 */


public class XMLParser {
    private final String PATH =  Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/UIAutoAdapt/";
    private final String XMLName = "AutoUIConfig.xml";
    private final String XML  = PATH + XMLName;
    private final String logTag = "[UIAutomatorAdapter]";
    public List<App> apps = null;

    public void init() {
        try {
            File desDir = new File(PATH);
            if (!desDir.exists()) {
                Log.e(logTag, "No folder existed: " + PATH);
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

    public boolean isFileExist() {
        File desDir = new File(PATH);
        if (!desDir.exists()) {
            return false;
        } else {
            return true;
        }
    }
}