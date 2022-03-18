package com.cy.tank;

import com.cy.tank.Mgr.PropertiesMgr;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import com.cy.tank.netty.Client;

public class T {
    public static void main(String[] args) {
       TankFrame tf = TankFrame.INSTANCE;
       tf.setVisible(true);

//        int initTankCount = Integer.parseInt((String) PropertiesMgr.get("initTankCount"));
        /*int initTankCount = Integer.parseInt((String) PropertiesMgr.getInstance().get("initTankCount"));

       // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(new Tank(50 + i * 100,100 + i * 10, Dir.DOWN, Group.BAD, tf));
        }*/

        // 背景音乐
        //new Thread(()->new Audio("audio/war1.wav").loop()).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connect();

    }
}
