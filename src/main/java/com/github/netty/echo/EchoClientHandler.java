package com.github.netty.echo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 功能描述: 客户端自定义处理器
 * @author: qinxuewu
 * @date: 2019/9/23 10:31
 * @since 1.0.0
 */
public class EchoClientHandler  extends SimpleChannelInboundHandler<ByteBuf>{


    /**
     * 从服务器接收到数据后调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("客户端接收到消息 received: " + ByteBufUtil.hexDump(msg.readBytes(msg.readableBytes())));
    }

    /**
     * 客户端连接服务器后被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端上线................");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }
}
