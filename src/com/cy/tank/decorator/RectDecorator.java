package com.cy.tank.decorator;

import com.cy.tank.GameObject;

import java.awt.*;
import java.time.Year;

public class RectDecorator extends GODecorator {

    public RectDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g) {

        this.x = getX();
        this.y = getY();
        go.paint(g);

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawRect(x, y, getWidth() + 2, getHeight() + 2);

        g.setColor(c);
    }

    @Override
    public int getX() {
        return go.getX();
    }

    @Override
    public int getY() {
        return go.getY();
    }

    @Override
    public int getWidth() {
        return go.getWidth();
    }

    @Override
    public int getHeight() {
        return go.getHeight();
    }

}
