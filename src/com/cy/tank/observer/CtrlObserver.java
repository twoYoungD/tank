package com.cy.tank.observer;

import com.cy.tank.GameModel;

public class CtrlObserver implements Observer {
    @Override
    public void ctrlPress(FireEvent e) {
        e.tank.fire();
    }
}
