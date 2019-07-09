package com.github;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 客户端业务处理
 * @author qxw
 * @version 1.00
 * @time   下午 4:16
 */

@ChannelHandler.Sharable   // 标记该类的实例可以被多个 Channel 共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     *  和服务器的连接已经建立之后将被调用；
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
    //    当被通知 Channel是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
    }


    /**
     * 每当从服务器接收到一条消息时被调用
     *  需要注意的是，由服务器发送的消息可能会被分块接收，也就是说，如果服务器发送了 5 字节， 那么不能保证这 5 字节会被一次性接收
     *  可能会被调用两次
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg)  {
        System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 在发生异常时，记录错误并关闭Channel
        cause.printStackTrace();
        ctx.close();
    }
}
