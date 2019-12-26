package com.github.netty.echo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 功能描述: 服务端启动程序
 * @author: qinxuewu
 * @date: 2019/9/23 10:15
 * @since 1.0.0
 */
public class EchoServer {
    private  final  int prot;

    public EchoServer(int port) {
           this.prot = port;
     }

    /**
     * •	创建ServerBootstrap实例来引导绑定和启动服务器
     * •	创建NioEventLoopGroup对象来处理事件，如接受新连接、接收数据、写数据等等
     * •	指定InetSocketAddress，服务器监听此端口
     * •	设置childHandler执行所有的连接请求
     * •	都设置完毕了，最后调用ServerBootstrap.bind() 方法来绑定服务器
     * @throws Exception
     */
     public void   start()throws  Exception{
         // 建立连接的线程
         EventLoopGroup bossGroup=new NioEventLoopGroup();
         // 处理连接
         EventLoopGroup workGroup=new NioEventLoopGroup();
         try {
             ServerBootstrap serverBootstrap=new ServerBootstrap();
             serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).localAddress(prot)
                     // 定义初始化器  可以添加多个hander
                     .childHandler(new ChannelInitializer<Channel>(){
                         @Override
                         protected void initChannel(Channel channel) throws Exception {
                             channel.pipeline().addLast(new EchoServerHander());
                         }
                     });

             ChannelFuture future=serverBootstrap.bind().sync();
             System.out.println(EchoServer.class.getName() + "started and listen on “" +future.channel().localAddress());
             future.channel().closeFuture().sync();
         }finally {
             // 优雅关闭
             bossGroup.shutdownGracefully();
             workGroup.shutdownGracefully();
         }

     }
    public static void main(String[] args)  throws Exception  {
        new EchoServer(65535).start();
    }
}
