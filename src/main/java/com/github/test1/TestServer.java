package com.github.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 〈〉
 *
 * @author qinxuewu
 * @create 19/7/12上午8:20
 * @since 1.0.0
 */


public class TestServer {


    // netty实现的http请求    控制台测试：  curl "http://localhost:8899"
    public static void main(String[] args) throws Exception {
        // 建立连接的线程
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        // 处理连接
        EventLoopGroup workGroup=new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerlnitailzr()); // 定义初始化器  可以添加多个hander
            ChannelFuture future=serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        }finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
