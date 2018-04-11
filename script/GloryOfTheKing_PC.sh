#!/bin/bash

count=100
adb wait-for-device
adb push AutoUIConfig.xml /sdcard/UIAutoAdapt/

while [ $count -gt 0 ];
do 
    adb shell am instrument -w -r   -e debug false -e class 'com.sc.uiautomatoradapter.AutoTestAdapter' com.sc.uiautomatoradapter.test/android.support.test.runner.AndroidJUnitRunner
    count=`expr $(($count-1))`
done
