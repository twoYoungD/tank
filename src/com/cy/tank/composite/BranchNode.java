package com.cy.tank.composite;

import java.util.ArrayList;
import java.util.List;

public class BranchNode extends Node {

    String name;

    List<Node> nodes = new ArrayList<>();

    public void add(Node node) {
        nodes.add(node);
    }

    public BranchNode(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println(name);
    }
}
