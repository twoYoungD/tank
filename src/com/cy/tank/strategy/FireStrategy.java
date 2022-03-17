package com.cy.tank.strategy;

import com.cy.tank.T;
import com.cy.tank.Tank;

import java.io.Serializable;

/**
 * 发射不同类型的子弹
 */
public interface FireStrategy extends Serializable {

    void fire(Tank tank);

}
