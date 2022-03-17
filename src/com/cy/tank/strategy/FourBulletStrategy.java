package com.cy.tank.strategy;

import com.cy.tank.Audio;
import com.cy.tank.Bullet;
import com.cy.tank.Tank;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;

public class FourBulletStrategy implements FireStrategy{

    @Override
    public void fire(Tank t) {
        int bX = t.getX() + t.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + t.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            new Bullet(bX, bY, dir, t.getGroup());
        }

        if(t.getGroup() == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }
}
