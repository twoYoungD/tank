package com.cy.tank;

import com.cy.tank.Mgr.PropertiesMgr;
import com.cy.tank.Mgr.ResourceMgr;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import com.cy.tank.strategy.FireStrategy;
import com.cy.tank.strategy.FourBulletStrategy;
import com.cy.tank.strategy.OneBulletStrategy;

import java.awt.*;
import java.util.Random;

public class Tank {
    public Rectangle rect = new Rectangle();
    private int x, y;
    private Dir dir;
    private final int SPEED = 5;
    public TankFrame tf;
    Random random = new Random();

    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private boolean moving = true;
    private boolean living = true;
    private Group group;

    private FireStrategy defaultFS;

    public Tank(int x, int y, Dir dir,Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        // 初始化 rect 的值
        rect.x = x;
        rect.y = y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

//        if (group == Group.GOOD) defaultFS = new FourBulletStrategy();
//        else defaultFS = new OneBulletStrategy();
        String fSName;
        if (group == Group.GOOD) {
            fSName = (String) PropertiesMgr.getInstance().get("goodFS");
        } else {
            fSName = (String) PropertiesMgr.getInstance().get("badFS");
        }
        try {
            defaultFS = (FireStrategy) Class.forName(fSName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.explodes.add(new Explode(this.x, this.y, tf));
            tf.tanks.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }

        move();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        // 随机发射子弹
        if(this.group == Group.BAD && random.nextInt(100) > 96)
            this.fire();
        // 随机改变方向
        if (this.group == Group.BAD && random.nextInt(100) > 95)
            randomDir();

        // 设置边界
        boundsCheck();

        // 更新 rect
        rect.x = this.x;
        rect.y = this.y;

    }

    private void boundsCheck() {
        if (x < 0) x = 0;
        if (y < 30) y = 30;
        if (x + WIDTH > tf.GAME_WIDTH ) x = tf.GAME_WIDTH - WIDTH;
        if (y + HEIGHT > tf.GAME_HEIGHT) y = tf.GAME_HEIGHT - HEIGHT;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    /**
     * 发射子弹
     */
    public void fire() {
        defaultFS.fire(this);
    }

    public void die() {
        this.living = false;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

}
