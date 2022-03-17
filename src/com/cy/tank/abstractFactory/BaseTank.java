package com.cy.tank.abstractFactory;

import com.cy.tank.Mgr.ResourceMgr;
import com.cy.tank.TankFrame;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

import java.awt.*;

public abstract class BaseTank {

    public Rectangle rect = new Rectangle();
    public boolean moving = true;
    private Group group;
    private int WIDTH;
    private int HEIGHT;
    private boolean living = true;

    public abstract void paint(Graphics g);

    public abstract void fire();

    public abstract void die();


    public boolean isMoving() {
        return moving;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }
}
