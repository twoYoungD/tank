package com.cy.tank;

import com.cy.tank.Mgr.ResourceMgr;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 10;
    private int x, y;
    private Dir dir;

    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();

    private boolean living = true;
    private TankFrame tf;
    private Group group;

    private Rectangle rect = new Rectangle(); // 坦克所在的格子

    public Bullet(int x, int y, Dir dir,Group group, TankFrame tf) {
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

        tf.bullets.add(this);
    }

    public void paint(Graphics g) {
        if (!living) tf.bullets.remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

        move();
    }

    private void move() {
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
        if (x < 0 || y < 0 || x > tf.GAME_WIDTH || y > tf.GAME_HEIGHT) living = false;
        // 更新 rect
        rect.x = this.x;
        rect.y = this.y;
    }

    // 子弹打中坦克
    public void crashWith(Tank tank) {
        if (this.group == tank.getGroup()) return;
        if(rect.intersects(tank.rect)) {
            tank.die();
            this.die();
        }
    }

    private void die() {
        this.living = false;
    }

    public int getX() {
        return x;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
