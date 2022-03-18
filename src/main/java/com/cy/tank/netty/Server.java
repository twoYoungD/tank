package com.cy.tank.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap sbs = new ServerBootstrap();
        try {
            ChannelFuture f = sbs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new TankMsgEncoder())
                                    .addLast(new ServerChannelHandler());
                        }
                    })
                    .bind(8888)
                    .sync();

            System.out.println("服务器启动");
            ServerFrame.INSTANCE.updateServerMsg("服务器启动成功");

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        try {
            Msg tank = (Msg) msg;
            System.out.println(tank);
            Server.clients.writeAndFlush(msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
        /*ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] b = new byte[buf.readableBytes()]; //readableBytes 有效的字节数
            buf.getBytes(buf.readerIndex(), b);
            String receiveMsg = new String(b);
            if ("_bye_".equals(receiveMsg)) {
                ServerFrame.INSTANCE.updateServerMsg("客户端请求退出");
                Server.clients.remove(ctx.channel());
                ctx.close();
            }

            Server.clients.writeAndFlush(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}
