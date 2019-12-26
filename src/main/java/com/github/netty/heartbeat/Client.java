package com.github.netty.heartbeat;
import com.github.proto.hearbaet.PacketProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.Random;

public class Client {

    private static Channel ch;
    private static Bootstrap bootstrap;
    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new IdleStateHandler(0, 5, 0));
                            pipeline.addLast(new ClientHeartbeatHandler());
                        }
                    });

            // 连接服务器
            doConnect();

            // 模拟不定时发送向服务器发送数据的过程
            Random random = new Random();
            while (true) {
                int num = random.nextInt(21);
                Thread.sleep(num * 1000);
                PacketProto.Packet builder = PacketProto.Packet.newBuilder().setPacketType(PacketProto.Packet.PacketType.DATA).
                        setData("我是数据包（非心跳包） " + num).setPacketType(PacketProto.Packet.PacketType.DATA).build();
                ch.writeAndFlush(builder);
       }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    /**
     * 抽取出该方法 (断线重连时使用)
     *
     * @throws InterruptedException
     */
    public static void doConnect() throws  Exception  {

        ch = bootstrap.connect("127.0.0.1", 8899).sync().channel();
    }
}
