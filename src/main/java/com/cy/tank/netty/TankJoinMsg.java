package com.cy.tank.netty;

import com.cy.tank.Tank;
import com.cy.tank.TankFrame;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import lombok.Data;

import java.io.*;
import java.util.UUID;

@Data
public class TankJoinMsg extends Msg {
    private int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private UUID id;

    public TankJoinMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
        this.id = tank.getId();
    }

    public TankJoinMsg() {
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    @Override
    public void handler() {
        //判断是否是 自己的坦克或者已经加入的坦克
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())
                || TankFrame.INSTANCE.findByUUId(this.id) != null) return;

        System.out.println(this);
        Tank t = new Tank(this);
        TankFrame.INSTANCE.addTank(t);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
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
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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
        return MsgType.TankJoin;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.setX(in.readInt());
            this.setY(in.readInt());
            this.setDir(Dir.values()[in.readInt()]);
            this.setMoving(in.readBoolean());
            this.setGroup(Group.values()[in.readInt()]);
            this.setId(new UUID(in.readLong(), in.readLong()));
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
