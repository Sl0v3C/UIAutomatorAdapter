package com.sc.uiautomatoradapter;

import java.io.InputStream;
import java.util.List;

/**
 * Created by pyy on 2017/9/19.
 */

public interface ActionParser {
    List<App> parse(InputStream is) throws Exception;
}
