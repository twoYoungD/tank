package com.cy.tank.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankMsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
        MsgType msgType = msg.getMsgType();
        byte[] bytes = msg.toBytes();
        int length = bytes.length;
        out.writeInt(msgType.ordinal());
        out.writeInt(length);
        out.writeBytes(msg.toBytes());
    }
}
