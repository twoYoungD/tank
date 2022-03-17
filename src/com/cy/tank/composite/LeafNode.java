package com.cy.tank.composite;

public class LeafNode extends Node {

    String name;

    public LeafNode(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println(name);
    }
}
