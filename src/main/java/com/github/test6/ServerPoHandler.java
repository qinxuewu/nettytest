package com.github.test6;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端业务处理器
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 11:08
 */
public class ServerPoHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        System.err.println("收到客户端的消息:" + message.getId());
//        ctx.writeAndFlush(message);

        /**
         * 当客户端20秒没往服务端发送过数据，就会触发IdleState.WRITER_IDLE事件，
         * 这个时候我们就像服务端发送一条心跳数据，跟业务无关，只是心跳。服务端收到心跳之后就会回复一条消息，表示已经收到了心跳的消息，只要收到了服务端回复的消息，
         * 那么就不会触发IdleState.READER_IDLE事件，如果触发了IdleState.READER_IDLE事件就说明服务端没有给客户端响应，这个时候可以选择重新连接。
         */
        if (message.getContent().equals("1")) {
            message.setContent("服务端返回 ping..........");
            ctx.writeAndFlush(message);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
