package com.sc.uiautomatoradapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pyy on 2017/9/19.
 */

public class Main extends Activity {
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 727;
    static final String PATH =  Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/UIAutoAdapt/";
    static final String XMLName = "AutoUIConfig.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            }
        }
        init();
        finish();
    }

    private void init() {
        try {
            File desDir = new File(PATH);
            if (!desDir.exists()) {
                desDir.mkdir();
            }

            File file = new File(PATH + XMLName);
            if (!file.exists()) { // sdcard目录没有该文件时才从assets目录里拷贝
                //将assets中的内容以流的形式展示出来
                InputStream inStream = getResources().getAssets().open(XMLName);
                //to为要写入sdcard中的文件名称
                OutputStream fs = new BufferedOutputStream(new FileOutputStream(PATH + XMLName));
                byte[] buffer = new byte[1024];
                int byteread = 0;
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    showDialogTipUserRequestPermission();
                }
                return;
            }
        }
    }

    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("需要获取存储空间，存储个人信息,\n否则，您将无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }
}
