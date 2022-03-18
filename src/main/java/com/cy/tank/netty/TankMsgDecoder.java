package com.cy.tank.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 8) return;

        in.markReaderIndex();

        MsgType type = MsgType.values()[in.readInt()];
        int length = in.readInt();

        if (in.readableBytes() < length) { // tcp 拆包 粘包
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Msg msg = null;

        switch (type) {
            case TankJoin:
                msg = new TankJoinMsg();
                break;
            case TankMove:
                msg = new TankMoveMsg();
                break;
            case TankStop:
                msg = new TankStopMsg();
                break;
            default:
                break;
        }
        msg.parse(bytes);
        out.add(msg);


    }
}
