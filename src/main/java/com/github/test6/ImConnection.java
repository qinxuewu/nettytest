package com.github.test6;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ImConnection {

    private Channel channel;
    public Channel connect(String host, int port) {
        doConnect(host, port);
        return this.channel;
    }
    private void doConnect(String host, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {

                    /**
                     * 第一个参数 60 表示读操作空闲时间
                     * 第二个参数 20 表示写操作空闲时间
                     * 第三个参数 60*10 表示读写操作空闲时间
                     * 第四个参数 单位/秒
                     */
                    ch.pipeline().addLast("ping", new IdleStateHandler(60, 20, 60 * 10, TimeUnit.SECONDS));
                    //实体类传输数据，jdk序列化
                    ch.pipeline().addLast("decoder", new MessageDecoder());
                    ch.pipeline().addLast("encoder", new MessageEncoder());
                    ch.pipeline().addLast(new ClientPoHandler());
                    //字符串传输数据
                    /*ch.pipeline().addLast("decoder", new StringDecoder());
                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new ClientStringHandler());*/
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.addListener(new ConnectionListener());
            channel = f.channel();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
