package com.cy.tank.observer;

import com.cy.tank.T;
import com.cy.tank.Tank;

public class FireEvent extends Event<Tank> {

    Tank tank;

    public FireEvent(Tank tank) {
        this.tank = tank;
    }

    @Override
    public Tank getResource() {
        return tank;
    }
}
