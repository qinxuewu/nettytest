package com.github.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;


/**
 * 功能描述: 服务端业务处理handler
 * @author: qinxuewu
 * @date: 2019/12/26 14:28
 * @since 1.0.0
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len=msg.getLen();
        byte[] content=msg.getContent();
        System.out.println("服务端接收到信息: "+ new String(content, Charset.forName("utf-8")));
//        System.out.println("长度=" + len);
        System.out.println("服务器接收到消息包数量=" + (++this.count));

        //回复消息
        String responseContent = UUID.randomUUID().toString();
        int responseLen = responseContent.getBytes("utf-8").length;
        byte[]  responseContent2 = responseContent.getBytes("utf-8");
        //构建一个协议包
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(responseContent2);

        ctx.writeAndFlush(messageProtocol);

    }
}
