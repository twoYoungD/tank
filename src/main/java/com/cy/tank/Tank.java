package com.cy.tank;

import com.cy.tank.Mgr.ResourceMgr;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import com.cy.tank.netty.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank {
    public Rectangle rect = new Rectangle();
    private int x, y;
    private Dir dir;
    private boolean moving = false;
    private Group group;
    private UUID id = UUID.randomUUID();
    private final int SPEED = 5;
    public TankFrame tf;
    Random random = new Random();

    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private boolean living = true;

    public Tank(TankJoinMsg msg) {
        this.id = msg.getId();
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.moving = msg.isMoving();
        this.group = msg.getGroup();
    }

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

    }

    public void paint(Graphics g) {
        if (!living) {
            tf.explodes.add(new Explode(this.x, this.y, tf));
            tf.removeByUUid(this.getId());
        }

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), x - 10, y - 10);
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
//        if(this.group == Group.BAD && random.nextInt(100) > 96)
//            this.fire();
//        // 随机改变方向
//        if (this.group == Group.BAD && random.nextInt(100) > 95)
//            randomDir();

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
        int bX = this.getX() + this.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.getY() + this.HEIGHT / 2 - Bullet.HEIGHT / 2;

        new Bullet(bX, bY, this.getDir(), this.getGroup());

        if(this.getGroup() == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
