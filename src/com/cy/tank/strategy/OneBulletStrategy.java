package com.cy.tank.strategy;

import com.cy.tank.Audio;
import com.cy.tank.Bullet;
import com.cy.tank.GameModel;
import com.cy.tank.Tank;
import com.cy.tank.decorator.LineDecorator;
import com.cy.tank.decorator.RectDecorator;
import com.cy.tank.enums.Group;

public class OneBulletStrategy implements FireStrategy{

    @Override
    public void fire(Tank t) {
        int bX = t.getX() + t.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + t.HEIGHT / 2 - Bullet.HEIGHT / 2;

        // 装饰器
//        GameModel.getInstance().add(
//            new RectDecorator(
//                new LineDecorator(
//                    new Bullet(bX, bY, t.getDir(), t.getGroup()))
//            )
//        );

        new Bullet(bX, bY, t.getDir(), t.getGroup());


        if(t.getGroup() == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }
}
