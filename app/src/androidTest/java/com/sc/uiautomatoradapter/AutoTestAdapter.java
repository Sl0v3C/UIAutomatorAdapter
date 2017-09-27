package com.sc.uiautomatoradapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by pyy on 2017/9/19.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
public class AutoTestAdapter {
    private UiDevice mDevice;
    private final long timeout = 10 * 1000;
    private Logger logger;
    private XMLParser parser;
    static final String logTag = "[UIAutomatorAdapter]";
    private String appName;
    private String result = null;

    // launch app by package name
    private boolean launchPackage(String app) {
        appName = app;
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        Intent mBootUpIntent = pm.getLaunchIntentForPackage(app);
        if (mBootUpIntent == null) {
            Log.i(logTag, "Cannot get intent from app: " + app);
            return false;
        }
        mBootUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mBootUpIntent.setAction(Intent.ACTION_MAIN);
        mBootUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        InstrumentationRegistry.getContext().startActivity(mBootUpIntent);
        return true;
    }

    // use "am force-stop" to stop current running app
    private void stopApp(UiDevice device, String app) {
        String stopCmd = "am force-stop " + app + "\n";
        try {
            device.executeShellCommand(stopCmd);
            appName = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // delay(ms), wrapper of Thread.sleep(ms)
    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws IOException, RemoteException {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
        // wake up the device when screen is off
        if (!mDevice.isScreenOn()) {
            mDevice.wakeUp();
            mDevice.swipe(mDevice.getDisplayWidth() / 2, mDevice.getDisplayHeight() - 100,
                    mDevice.getDisplayWidth() / 2, mDevice.getDisplayHeight() / 2, 5);
        }
        // Start from the home screen
        mDevice.pressHome();
        // launch "com.sc.uiautomatoradapter" to grant the permission and copy XML file to SDCARD
        launchPackage("com.sc.uiautomatoradapter");

        UiObject2 allow = mDevice.wait(Until.findObject(By.res(
                "com.android.packageinstaller:id/permission_allow_button")), 2 * 1000);
        // click the allow button to grant the permission
        if (allow != null && allow.isClickable()) {
            allow.click();
            delay(5000);
        }
        // initialize the Logger instance
        logger = new Logger();
        logger.init();
        // initialize the XMLParser instance
        parser = new XMLParser();
        parser.init();
    }

    // Click the object find by Text
    private void clickUiobjectByText(String text) {
        UiObject2 t = mDevice.wait(Until.findObject(By.text(text)), timeout);
        if (t != null) {
            t.click();
            return;
        }
        Log.i(logTag, "Cannot find UiObject2 by text " + text);
    }

    // Click the object find by Resource-id
    private void clickUiobjectByRes(String res) {
        UiObject2 r = mDevice.wait(Until.findObject(By.res(res)), timeout);
        if (r != null) {
            r.click();
            return;
        }
        Log.i(logTag, "Cannot find UiObject2 by res " + res);
    }

    // Click the object find by Content-description
    private void clickUiobjectByDes(String desc) {
        UiObject2 d = mDevice.wait(Until.findObject(By.desc(desc)), timeout);
        if (d != null) {
            d.click();
            return;
        }
        Log.i(logTag, "Cannot find UiObject2 by desc " + desc);
    }

    // Special cases like pressHome, wakeup, pressBack, stopApp, etc.
    private void specialCase(String special) throws RemoteException, IOException {
        if (special.equals("stopApp")) {
            if (appName != null) {
                stopApp(mDevice, appName);
            }
        } else if (special.equals("pressHome")) {
            mDevice.pressHome();
        } else if (special.equals("pressBack")) {
            mDevice.pressBack();
        } else if (special.equals("wakeUp")) {
            if (!mDevice.isScreenOn()) {
                mDevice.wakeUp();
                mDevice.swipe(mDevice.getDisplayWidth() / 2,
                        mDevice.getDisplayHeight() - 100, mDevice.getDisplayWidth() / 2,
                        mDevice.getDisplayHeight() / 2, 5);
            }
        }  else if (special.equals("sleep")) {
                mDevice.sleep();
        } else if (special.contains("click,")) {
            String[] sArray = special.split(",");
            int[] array = new int[sArray.length];
            for(int i = 1; i < sArray.length; i++){
                array[i] = Integer.parseInt(sArray[i]);
            }
            if (array.length == 3) {
                mDevice.click(array[1], array[2]);
            }
        } else if (special.contains("Shell,")) {
            String[] sArray = special.split(",");
            result = mDevice.executeShellCommand(sArray[1]);
            Log.i(logTag, result);
        } else if (special.contains("Compare,")) {
            String[] sArray = special.split(",");
            String compare = sArray[2];
            if (result != null) {
                if (result.contains(compare)) {
                    logger.write(sArray[1] + " " + compare + " PASS");
                }
            }
        }
     }

    // process the Action List
    private void processAction(String type, String value) {
        if (type.equals("text")) {
            clickUiobjectByText(value);
        } else if (type.equals("resource")) {
            clickUiobjectByRes(value);
        } else if (type.equals("description")) {
            clickUiobjectByDes(value);
        } else if (type.equals("delay")) {
            delay(Integer.parseInt(value));
        } else if (type.equals("special")) {
            try {
                specialCase(value);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(logTag, "Unsupported type: " + type + " value: " + value);
        }
    }

    // the test entrance
    @Test
    public void allTests() throws IOException, UiObjectNotFoundException, InterruptedException {
        String appName, type, value;
        if (parser.apps != null) {
            for (App app : parser.apps) {
                appName = app.getName();
                if (launchPackage(appName)) {
                    if (app.actList != null) {
                        for (Action action : app.actList) {
                            type = action.getType();
                            value = action.getValue();
                            processAction(type, value);
                        }
                    }
                }
            }
        }
    }
}