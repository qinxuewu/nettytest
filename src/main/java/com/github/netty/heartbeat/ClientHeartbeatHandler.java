package com.github.netty.heartbeat;

import com.github.proto.hearbaet.PacketProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 *  自定义处理器
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 10:13
 */
public class ClientHeartbeatHandler  extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--- 通道处于活动状态 ---");
    }


    /**
     * 当被通知 Channel 是非活跃的时候,发 送一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--- 服务器处于非活动 ---");
        // 10s 之后尝试重新连接服务器
        System.out.println("5s 之后尝试重新连接服务器...");
        Thread.sleep(5 * 1000);
        Client.doConnect();
    }

    /**
     * 心跳检测  在出现超时事件时会被触发，包括读空闲超时或者写空闲超时
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("出现超时事件时触发...........");
        if (evt instanceof IdleStateEvent) {

          //  IdleStateEvent event = (IdleStateEvent)evt;  空闲事件

            // 不管是读事件空闲还是写事件空闲都向服务器发送心跳包
            sendHeartbeatPacket(ctx);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("连接出现异常");
//        cause.printStackTrace();
//        ctx.close();
    }

    /**
     * 发送心跳包
     * @param ctx
     */
    private void sendHeartbeatPacket(ChannelHandlerContext ctx) {
        PacketProto.Packet packet = PacketProto.Packet.newBuilder().setPacketType(PacketProto.Packet.PacketType.HEARTBEAT).build();
        ctx.writeAndFlush(packet);
    }
}
