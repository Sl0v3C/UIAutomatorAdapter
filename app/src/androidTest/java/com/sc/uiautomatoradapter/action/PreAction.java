package com.sc.uiautomatoradapter.action;

import android.graphics.Point;

public class PreAction {
    private String type;    // action type
    private String value;   // action value

    // get the action type
    public String getType() {
        return type;
    }

    // set the type
    public void setType(String type) {
        this.type = type;
    }

    // get the action value
    public String getValue() {
        return value;
    }

    // set the value
    public void setValue(String string) {
        this.value = string;
    }

    // String to int array
    private int[] getArray(String string) {
        String[] sArray = string.split(",");
        int[] array = new int[sArray.length];
        int i = 0;
        for (String element : sArray) {
            array[i++] = Integer.parseInt(element);
        }
        return array;
    }

    // save the swipe point[] from action value, which is String
    public void getSwipeP(Point[] swipeP) {
        int[] array = getArray(value);
        int size = array.length / 2;
        swipeP = new Point[size];
        for (int i = 0; i < array.length; i += 2) {
            Point temp = new Point(array[i], array[i + 1]);
            swipeP[i / 2] = temp;
        }
    }

    // save the drag point[] from action value, which is String
    public void getDragP(Point[] dragP) {
        int[] array = getArray(value);
        int size = array.length / 2;
        dragP = new Point[size];
        for (int i = 0; i < array.length; i += 2) {
            Point temp = new Point(array[i], array[i + 1]);
            dragP[i / 2] = temp;
        }
    }
}
