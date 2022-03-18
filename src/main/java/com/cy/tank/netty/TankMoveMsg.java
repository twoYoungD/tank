package com.cy.tank.netty;

import com.cy.tank.Tank;
import com.cy.tank.TankFrame;
import com.cy.tank.enums.Dir;
import com.cy.tank.enums.Group;
import lombok.Data;

import java.io.*;
import java.util.UUID;

@Data
public class TankMoveMsg extends Msg {
    private UUID id;
    private int x;
    private int y;
    private Dir dir;

    public TankMoveMsg() {
    }

    public TankMoveMsg(UUID id, int x, int y, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public TankMoveMsg(Tank t) {
        this.id = t.getId();
        this.x = t.getX();
        this.y = t.getY();
        this.dir = t.getDir();
    }

    @Override
    public void handler() {
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) return;
        Tank t = TankFrame.INSTANCE.findByUUId(id);
        if (t != null) {
            t.setMoving(true);
            t.setX(x);
            t.setY(y);
            t.setDir(dir);
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
        return MsgType.TankMove;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.setX(in.readInt());
            this.setY(in.readInt());
            this.setDir(Dir.values()[in.readInt()]);
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
