package com.cy.tank.chainOfResponsibility;

import com.cy.tank.GameObject;
import com.cy.tank.Mgr.PropertiesMgr;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ChainCollider implements Collider {

    private List<Collider> colliders = new LinkedList<>();

    Properties properties = PropertiesMgr.getInstance();

    public ChainCollider() {
//        add(new BulletTankCollider());
//        add(new TankTankCollider());
        String colliderList = (String) properties.get("collider");
        String[] colliderName = colliderList.split(",");
        try {
            for (int i = 0; i < colliderName.length; i++) {
                Collider clazz = (Collider) Class.forName(colliderName[i]).newInstance();
                add(clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    @Override
    public boolean collideWith(GameObject o1, GameObject o2) {
        for (Collider collider : colliders) {
            if (collider.collideWith(o1, o2)) return true;
        }
        return false;
    }
}
