package com.github.netty.echo;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 功能描述:  服务端自定义处理器
 * @author: qinxuewu
 * @date: 2019/9/23 10:20
 * @since 1.0.0
 */
public class EchoServerHander extends ChannelInboundHandlerAdapter {


    /**
     * 从当前Channel的对端读取消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到消息 received: " + msg);
        ctx.writeAndFlush(msg);
    }

    /**
     * 消息读取完成后执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端消息读取完成后执行............");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * 异常捕获处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
