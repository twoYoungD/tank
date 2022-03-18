package com.cy.tank.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {

    public static final ServerFrame INSTANCE = new ServerFrame();

    Button btnStart = new Button("start");
    TextArea leftA = new TextArea();
    TextArea rightA = new TextArea();

    private Server server = new Server();

    private ServerFrame() {
        this.setSize(1200, 500);
        this.setLocation(100,100);
        this.setTitle("server");
        this.add(btnStart, BorderLayout.NORTH);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(leftA);
        p.add(rightA);
        this.add(p);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        /*btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });*/

    }

    public void updateServerMsg(String msg) {
        leftA.setText(leftA.getText() + System.getProperty("line.separator") + msg);
    }

    public void updateClientMsg(String msg) {
        rightA.setText(rightA.getText() + System.getProperty("line.separator") + msg);
    }


    public static void main(String[] args) throws InterruptedException {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }
}
