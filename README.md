<img width="200" height="200" src="icon.png">

# UIAutomatorAdapter
An adapter for UIAutomator app which can be execute many UI tests by config the XML file.

## Usage
Install the ```app-debug.apk``` and ```app-debug-androidTest.apk```.  
Then you can execute command as follows:  
```adb shell am instrument -w -r   -e debug false -e class com.sc.uiautomatoradapter.AutoTestAdapter com.sc.uiautomatoradapter.test/android.support.test.runner.AndroidJUnitRunner```  
After the 1st time running, it will copy ```AutoUIConfig.xml``` to the ```/sdcard/UIAutoAdapt/```.  
User can pull ```AutoUIConfig.xml``` out and modify the content to run what they want.  
The default actions are launch Google Music and play music randomly & launch Calculator to do some calculation.  

## Requirements
Need Android OS >= 6.0, or you can fork the source code and build for a lower OS version.
At least, OS version must bigger than Jelly Bean, since UIAutomator can be work after this version.

## XML arch
The XML arch like as follows:  
```
<apps>
	<app>
		<name>VirtualAppScreenOff</name>
		<action>
			<type>special</type>
			<value>Swipe,533,1244,282,1493,526,1713,791,1452,Steps,20</value>
		</action>
	</app>
	<app>
		<name>VirtualApp</name>
		<action>
			<type>special</type>
			<value>click,727,1010</value>
		</action>
	</app>
	<app>
		<name>谷歌音乐</name>
		<action>
			<type>resource</type>
			<value>com.google.android.music:id/play_card</value>
		</action>
		<action>
			<type>delay</type>
			<value>20000</value>
		</action>
		<action>
			<type>special</type>
			<value>click,727,1010</value>
		</action>
		<action>
			<type>special</type>
			<value>Shell,tinymix 59</value>
		</action>
		<action>
			<type>special</type>
			<value>Compare,Speaker Test,>On</value>
		</action>
		<action>
			<type>special</type>
			<value>stopApp</value>
		</action>
	</app>
</apps>
```  
```<app>*</app>```means the app we want to launch and do something.  
**VirtualApp** is a virtual app name which can do something without launch any app.  
**VirtualAppScreenOff** do something screen off without launch any app.

```<name>*</name>```stands for the package name of the app we want to launch. e.g.: 计算器 or Calculator or the some name else.  
```<action>*</action>```stands for the action we want to do.  
```<type>*</type>```means the action type, now supports:
* text(click object find by text);
* resource(click object find by resource-id);
* description(click object find by content-description);
* delay
* special: wakeup, pressHome, pressBack, stopApp, sleep, click with X/Y value,  Shell, Compare, Swipe:  
* The example of ```click with X/Y value``` is ```click,100,200```. Use comma to separate values.  
* ```Shell,tinymix 59``` to execute shell command "tinymix 59".  
* ```Compare,Speaker Test,>On``` means compare the shell return value with ">On".So ```Compare,Type,Value``` Type will add in logger, value is the String will be compared with the return value of the "Shell command".  
* **Compare** action should be follow **Shell** action when you need to judge the status right or not.   
* Swipe can help us to do swipe cation. Like ```Swipe, x1, y1, x2, y2, ..., Steps,30.```   

```<value>*</value>```means the value of the type.


