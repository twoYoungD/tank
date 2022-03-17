package com.cy.tank.chainOfResponsibility;

import com.cy.tank.GameObject;

public interface Collider {

    // true表示相撞
    boolean collideWith(GameObject o1, GameObject o2);

}
