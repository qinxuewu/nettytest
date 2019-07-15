package com.github.test2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Netty scoker编程
 *
 * @author qinxuewu
 * @create 19/7/13上午10:22
 * @since 1.0.0
 */


public class MyServerHander  extends SimpleChannelInboundHandler<String> {

    // 接收消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)  throws Exception{
        System.out.println(".......server  channelRead0..........");
        ctx.channel().writeAndFlush("11");
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println("handlerAdded  新的通道添加时.........");

    }

    // 处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
