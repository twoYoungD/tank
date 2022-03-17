package com.cy.tank.abstractFactory;

import com.cy.tank.Bullet;
import com.cy.tank.Explode;
import com.cy.tank.Tank;
import com.cy.tank.TankFrame;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

public class DefaultFactory extends AbstractFactory{

    private static final AbstractFactory INSTANCE = new DefaultFactory();

    private DefaultFactory() {}

    public static AbstractFactory getInstance() {
        return INSTANCE;
    }

//    @Override
//    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame gm) {
//        return new Tank(x, y, dir, group, gm);
//    }
//
//    @Override
//    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame gm) {
//        return new Bullet(x, y, dir, group, gm);
//    }
//
//    @Override
//    public BaseExplode createExplode(int x, int y, TankFrame gm) {
//        return new Explode(x, y, gm);
//    }
}