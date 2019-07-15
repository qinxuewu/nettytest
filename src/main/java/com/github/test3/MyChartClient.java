package com.github.test3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * netty 多客户端连接与通信
 *
 * @author qinxuewu
 * @create 19/7/14下午11:58
 * @since 1.0.0
 */


public class MyChartClient {

    public static void main(String[] args) throws  Exception{
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new MyChartClientInitailzr());

            Channel channel=bootstrap.connect("localhost",8899).sync().channel();

            // 接收控制台输入
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            for (; ;){
                // 不断循环获取用户在控制台的输入 发送到server
                channel.writeAndFlush(br.readLine()+"\r\n");
            }

        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
