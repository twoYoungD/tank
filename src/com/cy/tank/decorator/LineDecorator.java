package com.cy.tank.decorator;

import com.cy.tank.GameObject;

import java.awt.*;

public class LineDecorator extends GODecorator {

    public LineDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g) {

        this.x = getX();
        this.y = getY();
        go.paint(g);

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawLine(x, y, x + 10, y + 10);

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
