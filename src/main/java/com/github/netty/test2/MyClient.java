package com.github.netty.test2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty 实现scokert
 *
 * @author qinxuewu
 * @create 19/7/13上午10:46
 * @since 1.0.0
 */


public class MyClient {

    public static void main(String[] args) throws  Exception{
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitailzr());

            ChannelFuture channelFuture=bootstrap.connect("localhost",8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
