package com.cy.tank.chainOfResponsibility;

import com.cy.tank.Bullet;
import com.cy.tank.GameObject;
import com.cy.tank.Tank;

public class BulletTankCollider implements Collider{
    @Override
    public boolean collideWith(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet b = (Bullet) o1;
            Tank t = (Tank) o2;
            if (b.getGroup() == t.getGroup()) return false;
            if(b.getRect().intersects(t.getRect())) {
                t.die();
                b.die();
                return true;
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            collideWith(o2, o1);
        }
        return false;
    }
}
