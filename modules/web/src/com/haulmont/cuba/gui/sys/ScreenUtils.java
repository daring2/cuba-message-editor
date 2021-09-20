package com.haulmont.cuba.gui.sys;

import com.haulmont.cuba.gui.config.WindowInfo;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ScreenUtils {

    public static String getScreenMessagePack(ScreensHelper helper, WindowInfo window) {
        String pack = null;
        try {
            var template = window.getTemplate();
            if (isNotBlank(template)) {
                var el = helper.getWindowElement(template);
                pack = el.attributeValue("messagesPack");
            }
        } catch (Exception e) {
            // ignore
        }
        if (pack == null) {
            pack = window.getControllerClass().getPackageName();
        }
        return pack;
    }

    private ScreenUtils() {
    }
}
