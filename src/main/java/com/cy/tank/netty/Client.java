package com.cy.tank.netty;

import com.cy.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    private Channel channel;

    public static final Client INSTANCE = new Client();

    private Client() {}

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInit())
                    .connect("localhost", 8888)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                System.out.println("连接成功");
                                channel = future.channel();
                            } else {
                                System.out.println("连接失败");
                            }
                        }
                    })
                    .sync();

            System.out.println("客户端启动");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }

    public void closeConnect() {
//        this.send("_bye_");
        System.out.println("客户端退出");
    }
}

class ChannelInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TankMsgEncoder())
                .addLast(new TankMsgDecoder())
                .addLast(new ChannelInAndOutHandler());
    }
}

class ChannelInAndOutHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] b = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), b);
            String s = new String(b);
        } finally {
            if (buf != null) {
                ReferenceCountUtil.release(buf);
            }
        }
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println(msg);
        msg.handler();
        /*ByteBuf buf = null;
        try {
            byte[] b = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), b);
            String s = new String(b);
        } finally {
            if (buf != null) {
                ReferenceCountUtil.release(buf);
            }
        }*/
    }
}

