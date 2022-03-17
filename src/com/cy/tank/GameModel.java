package com.cy.tank;

import com.cy.tank.Mgr.PropertiesMgr;
import com.cy.tank.abstractFactory.BaseTank;
import com.cy.tank.chainOfResponsibility.BulletTankCollider;
import com.cy.tank.chainOfResponsibility.ChainCollider;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    public Tank myTank;

    private List<GameObject> objects = new ArrayList<>();

    private ChainCollider collider = new ChainCollider();

    private GameModel() {}

    private void init() {
        // 初始化我方坦克
        myTank = new Tank(200, 300, Dir.UP, Group.GOOD);

        // 初始化敌方坦克
        int initTankCount = Integer.parseInt((String) PropertiesMgr.getInstance().get("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            add(new Tank(50 + i * 100,100 + i * 10, Dir.DOWN, Group.BAD));
        }

        // 初始化墙
        add(new Wall(100,100,200, 50));
        add(new Wall(600,100,50, 200));
        add(new Wall(200,300,50, 200));
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void add(GameObject gameObject) {
        objects.add(gameObject);
    }

    public void remove(GameObject go) {
        objects.remove(go);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹的数量：" + bullets.size(), 10,60);
//        g.drawString("坦克的数量：" + tanks.size(), 10,80);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }

        // 互相碰撞
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                collider.collideWith(o1, o2);
            }
        }

//        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).crashWith(tanks.get(j));
//            }
//        }

    }

    public Tank getMainTank() {
        return myTank;
    }

    // 存盘/保存快照
    public void save() {
        File f = new File("/Users/jiangzuopeng/Documents/tank.data");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(myTank);
            oos.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 加载快照
    public void load() {
        File f;
        ObjectInputStream ois = null;
        try {
            f = new File("/Users/jiangzuopeng/Documents/tank.data");
            ois = new ObjectInputStream(new FileInputStream(f));
            myTank = (Tank) ois.readObject();
            objects = (List<GameObject>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
