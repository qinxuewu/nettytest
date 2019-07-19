package com.github.protobuf;

import com.github.test4.MyCheckServerInitailzr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *  protobuf集成Netty与多协议消息传递
 *
 * @author qinxuewu
 * @create 19/7/16下午9:31
 * @since 1.0.0
 */


public class ProtoServer {
    public static void main(String[] args) throws  Exception {
        // 建立连接的线程
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        // 处理连接
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 日志haner
                    .childHandler(new ProtoServerInitalizr());

            ChannelFuture future=serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        }finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
