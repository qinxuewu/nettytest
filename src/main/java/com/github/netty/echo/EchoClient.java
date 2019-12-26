package com.github.netty.echo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 功能描述: 客户端
 * @author: qinxuewu
 * @date: 2019/9/23 10:26
 * @since 1.0.0
 */
public class EchoClient {
    private final String host;
   private final int port;

    public EchoClient(String host, int port) {
             this.host = host;
             this.port = port;
    }

    public  void  start()throws  Exception{
            EventLoopGroup group = new NioEventLoopGroup();
            try {

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                        .handler(new ChannelInitializer<SocketChannel>(){
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new EchoClientHandler());
                            }
                        });

                ChannelFuture channelFuture=bootstrap.connect().sync();
                channelFuture.channel().closeFuture().sync();
            }finally {
                group.shutdownGracefully().sync();
            }
        }

    public static void main(String[] args) throws Exception {
          new EchoClient("localhost", 65535).start();
      }

}


