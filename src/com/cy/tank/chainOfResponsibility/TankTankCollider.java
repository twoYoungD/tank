package com.cy.tank.chainOfResponsibility;

import com.cy.tank.GameObject;
import com.cy.tank.Tank;

public class TankTankCollider implements Collider{
    @Override
    public boolean collideWith(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;
            if (t1.getRect().intersects(t2.getRect())) {
                t1.rollback();
                t2.rollback();
            }
        }
        // 两个敌方坦克相撞
        return false;
    }
}
