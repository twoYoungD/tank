package com.cy.tank.decorator;

import com.cy.tank.GameObject;

import java.awt.*;

public abstract class GODecorator extends GameObject {

    GameObject go;

    public GODecorator(GameObject go) {
        this.go = go;
    }

    public void paint(Graphics g) {
        go.paint(g);
    }

}
