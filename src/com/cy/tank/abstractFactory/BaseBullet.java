package com.cy.tank.abstractFactory;

import java.awt.*;

public abstract class BaseBullet {

    public abstract void paint(Graphics g);

    public abstract void crashWith(BaseTank tank);

}
