package com.cy.tank.chainOfResponsibility;

import com.cy.tank.Bullet;
import com.cy.tank.GameObject;
import com.cy.tank.Tank;
import com.cy.tank.Wall;

public class TankWallCollider implements Collider{
    @Override
    public boolean collideWith(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;

            if(t.getRect().intersects(w.getRect())) {
                t.rollback();
            }
        } else if (o1 instanceof Wall && o2 instanceof Bullet) {
            collideWith(o2, o1);
        }
        return false;
    }
}
