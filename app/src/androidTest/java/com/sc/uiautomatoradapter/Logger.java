package com.sc.uiautomatoradapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pyy on 2017/9/19.
 */

public class Logger {
    private final String LOGFILE  = Main.PATH + "log.txt";
    public FileOutputStream mOut = null;

    public void init() {
        try {
            File file = new File(LOGFILE);
            file.createNewFile();

            if (file.exists()) {
                mOut = new FileOutputStream(file);
                if (mOut == null) {
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        if (mOut != null) {
            mOut.close();
            mOut = null;
        }
        super.finalize();
    }

    private void writeContent(String text) {
        StringBuilder content = new StringBuilder(text);
        content.append("\n");
        try {
            mOut.write(content.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String text) {
        try {
            writeContent(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
