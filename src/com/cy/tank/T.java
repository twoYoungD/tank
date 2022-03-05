package com.cy.tank;

import com.cy.tank.Mgr.PropertiesMgr;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

public class T {
    public static void main(String[] args) throws InterruptedException {
       TankFrame tf = new TankFrame();

//        int initTankCount = Integer.parseInt((String) PropertiesMgr.get("initTankCount"));
        int initTankCount = Integer.parseInt((String) PropertiesMgr.getInstance().get("initTankCount"));

       // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(new Tank(50 + i * 100,100 + i * 10, Dir.DOWN, Group.BAD, tf));
        }

        // 背景音乐
        //new Thread(()->new Audio("audio/war1.wav").loop()).start();

       while (true) {
           Thread.sleep(50);
           tf.repaint();
       }
    }
}
