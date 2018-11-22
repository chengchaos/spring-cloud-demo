package com.example.mytimer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyWcStart {

    public static void main(String[] args) {

        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGoup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGoup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new MyWebSocketChannelHandler());

            Channel channel = bootstrap.bind(8888)
                    .sync()
                    .channel();

            channel.closeFuture().sync();

        } catch (Exception e) {

        } finally {
            boosGroup.shutdownGracefully();
            workGoup.shutdownGracefully();
        }
    }
}
