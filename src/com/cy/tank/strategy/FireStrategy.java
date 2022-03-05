package com.cy.tank.strategy;

import com.cy.tank.T;
import com.cy.tank.Tank;

/**
 * 发射不同类型的子弹
 */
public interface FireStrategy {

    void fire(Tank tank);

}
