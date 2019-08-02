package com.github.heartbeat;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import com.github.proto.hearbaet.PacketProto;

public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup acceptorGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(acceptorGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 编解码器
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            pipeline.addLast(new ProtobufDecoder(PacketProto.Packet.getDefaultInstance()));
                            // 空闲检测时间
                            pipeline.addLast(new IdleStateHandler(6, 0, 0));
                            // 自定义处理器
                            pipeline.addLast(new ServerHeartbeatHandler());
                        }
                    });
            Channel ch = bootstrap.bind(8899).sync().channel();
            System.out.println("Server has started...");
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            acceptorGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
