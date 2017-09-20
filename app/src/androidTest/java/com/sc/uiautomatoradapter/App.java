package com.sc.uiautomatoradapter;

import java.util.List;

/**
 * Created by pyy on 2017/9/19.
 */

public class App {
    private String name;            // app name, used by launchPackage
    public List<Action> actList;    // action list

    // get the app name
    public String getName() {
        return this.name;
    }

    // set the name
    public void setName(String name) {
        this.name = name;
    }

}
