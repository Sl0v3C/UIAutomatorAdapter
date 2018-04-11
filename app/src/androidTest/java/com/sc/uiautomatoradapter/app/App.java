package com.sc.uiautomatoradapter.app;

import com.sc.uiautomatoradapter.action.PreAction;
import com.sc.uiautomatoradapter.action.Action;

import java.util.List;

/**
 * Created by pyy on 2017/9/19.
 */

public class App {
    private String name;            // app name, used by launchPackage
    public List<Action> actList;    // action list
    public List<PreAction> preActList;    // action list

    // get the app name
    public String getName() {
        return this.name;
    }

    // set the name
    public void setName(String name) {
        this.name = name;
    }

}
