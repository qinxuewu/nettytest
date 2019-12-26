package com.github.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * TCP粘包拆包
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 11:32
 */
public class Client {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Channel channel = null;
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            // 用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接
            // 当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder", new StringDecoder());
                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            System.err.println("client:" + msg.toString());
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 8899).sync();
            channel = f.channel();
            StringBuilder msg = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                msg.append("hello qinxuewu");
            }
//            channel.writeAndFlush(msg + System.getProperty("line.separator"));
            // 自定义换行符
            channel.writeAndFlush(msg + "_");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
