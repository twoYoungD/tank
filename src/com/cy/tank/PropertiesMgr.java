package com.cy.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertiesMgr {

    public static Properties props = new Properties();

    static {
        try {
            props.load(PropertiesMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }

    public static void main(String[] args) {
        Object initTankCount = PropertiesMgr.get("initTankCount");
        System.out.println(initTankCount);
    }
}
