package com.github.netty.test5;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.time.LocalDateTime;


/**
 *  文本处理器 处理消息的handler
 *   TextWebSocketFrame：在netty中，是用于websocket专门处理文本的对象，frame是消息的载体。
 * @author qxw
 * @version 1.00
 * @time  15/7/2019 下午 1:09
 */
public class TextWebScokerFramerHander extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 通道列表
     */
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        //1.获取客户端发来的消息
        String content = msg.text();
        Channel currentChannel = ctx.channel();
        System.out.println("收到消息："+msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:"+ LocalDateTime.now()));

    }


    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channel，并且放到ChannelGroup中去进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : group) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
        }
        group.add(ctx.channel());
        System.err.println("Client:" + incoming.remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : group) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        }
        System.err.println("Client:" + incoming.remoteAddress() + "离开");
        group.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常关闭");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("channel关闭前");
            }
        }
    }
}
