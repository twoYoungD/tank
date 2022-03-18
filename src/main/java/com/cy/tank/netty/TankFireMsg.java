package com.cy.tank.netty;

import com.cy.tank.Bullet;
import com.cy.tank.Tank;
import com.cy.tank.TankFrame;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import lombok.Data;

import java.io.*;
import java.util.UUID;

@Data
public class TankFireMsg extends Msg {
    private UUID tankId;
    private int x, y;
    private Dir dir;
    private Group group;

    public TankFireMsg(Tank t) {
        int bX = t.getX() + t.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = t.getY() + t.HEIGHT / 2 - Bullet.HEIGHT / 2;
    }
    public TankFireMsg() {
    }

    public TankFireMsg(UUID tankId, int x, int y, Dir dir, Group group) {
        this.tankId = tankId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    @Override
    public void handler() {
        if (this.tankId.equals(TankFrame.INSTANCE.getMainTank().getId()))
            return;
        Tank t = TankFrame.INSTANCE.findByUUId(tankId);
        if (t != null) {
            t.fire();
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(tankId.getMostSignificantBits());
            dos.writeLong(tankId.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankFire;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.setX(in.readInt());
            this.setY(in.readInt());
            this.setDir(Dir.values()[in.readInt()]);
            this.setGroup(Group.values()[in.readInt()]);
            this.setTankId(new UUID(in.readLong(), in.readLong()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
