package com.cy.tank.Mgr;

import java.io.IOException;
import java.util.Properties;

public class PropertiesMgr {

    private static Properties props;

    private PropertiesMgr() {

    }

    public static Properties getInstance() {
        if (props == null) props = new Properties();
        try {
            props.load(PropertiesMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }

    public static void main(String[] args) {
        Object initTankCount = PropertiesMgr.getInstance().get("initTankCount");
        System.out.println(initTankCount);
    }
}
