package com.cy.tank;

import com.cy.tank.Mgr.PropertiesMgr;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

public class T {
    public static void main(String[] args) throws InterruptedException {
       TankFrame tf = new TankFrame();

        // 背景音乐
        // new Thread(()->new Audio("audio/war1.wav").loop()).start();

       while (true) {
           Thread.sleep(50);
           tf.repaint();
       }
    }
}
