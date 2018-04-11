#!/bin/bash

adb wait-for-device
adb intall -r UIAutomator-main.apk
adb instal -r UIAutomator-autoRun.apk

