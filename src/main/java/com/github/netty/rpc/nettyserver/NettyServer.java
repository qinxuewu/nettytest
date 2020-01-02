package com.github.netty.rpc.nettyserver;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 功能描述: Netty服务端
 * @author: qinxuewu
 * @date: 2020/1/2 10:16
 * @since 1.0.0
 */
public class NettyServer {

    public  static  void  startServer(String hostName,int port){
        startServre0(hostName,port);
    }

    private  static  void  startServre0(String hostName,int port){
        // 创建boos线程组 设置线程数1  负责接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 创建工作线程组 线程数默认是CPU*2  专门负责网络的读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加Netty自带编解码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new NettyServerHandler()); //业务处理器
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(hostName, port).sync();
            System.out.println("服务提供方开始提供服务~~");
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
