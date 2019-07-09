package com.github;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import io.netty.util.CharsetUtil;

/**
 *  服务单业务处理   负责接收并响应事件通知
 *   标示一个ChannelHandler 可以被多个 Channel 安全地共享
 * @author qxw
 * @version 1.00
 * @time  9/7/2019 下午 3:30
 */

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

    /**
     * 处理所有接收到的数据
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        // 将消息记录到控制条
        System.out.println("Service 接收消息： "+in.toString(CharsetUtil.UTF_8));
//      将接收到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将未决消息冲刷到远程节点，并且关闭该 Channel
       ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 重写 exceptionCaught()方法允许你对 Throwable 的任何子类型做出反应， 在这里你
     * 记录了异常并关闭了连接。
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();   // 打印异常栈
        ctx.close();               // 关闭Channel
    }

}
