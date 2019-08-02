package com.github.heartbeat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import static  com.github.proto.hearbaet.PacketProto.Packet;


/**
 * 自定义处理器
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 10:28
 */
public class ServerHeartbeatHandler extends ChannelInboundHandlerAdapter {
    // 心跳丢失计数器
    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        System.out.println(channel.remoteAddress()+" 客户端上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        System.out.println(channel.remoteAddress()+" 客户单已下线");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 判断接收到的包类型
        if (msg instanceof Packet) {
           Packet packet = (Packet) msg;
           // 判断接收包类型
            switch (packet.getPacketType()) {
                case HEARTBEAT:
                    // 心跳包
                    handleHeartbreat(ctx, packet);
                    break;

                case DATA:
                    // 数据包
                    handleData(ctx, packet);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 空闲6s之后触发 (心跳包丢失)
            if (counter >= 3) {
                // 连续丢失3个心跳包 (断开连接)
                ctx.channel().close().sync();
                System.out.println("已与Client断开连接  ;"+ ctx.channel().remoteAddress());
            } else {
                counter++;
                System.out.println("丢失了第 " + counter + " 个心跳包: "+ctx.channel().remoteAddress());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("连接出现异常");
    }

    /**
     * 处理心跳包
     *
     * @param ctx
     * @param packet
     */
    private void handleHeartbreat(ChannelHandlerContext ctx, Packet packet) {
        // 将心跳丢失计数器置为0
        counter = 0;
        System.out.println("收到心跳包："+ctx.channel().remoteAddress());
        ReferenceCountUtil.release(packet);
    }

    /**
     * 处理数据包
     *
     * @param ctx
     * @param packet
     */
    private void handleData(ChannelHandlerContext ctx, Packet packet) {
        // 将心跳丢失计数器置为0
        counter = 0;
        String data = packet.getData();
        System.out.println(data);
        ReferenceCountUtil.release(packet);
    }
}
