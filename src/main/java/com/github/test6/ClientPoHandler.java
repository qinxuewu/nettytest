package com.github.test6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 客户端自定义处理器
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 11:16
 */
public class ClientPoHandler extends ChannelInboundHandlerAdapter {
    private ImConnection imConnection = new ImConnection();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        System.out.println("client:" + message.getContent());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("掉线了...");
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                imConnection.connect(ImClientApp.HOST, ImClientApp.PORT);
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("长期没收到服务器推送数据");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("长期未向服务器发送数据");
                //发送心跳包
                Message message = new Message();
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                message.setId(id);
                message.setContent("1");
                ctx.writeAndFlush(message);
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL");
            }
        }
    }
}
