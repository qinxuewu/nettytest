package com.github.test2;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;




/**
 *  netty 实现scokert
 * @author qinxuewu
 * @create 19/7/13上午10:46
 * @since 1.0.0
 */


public class MyClientHander  extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg){
        System.out.println("client addd...."+ctx.channel().remoteAddress());
        System.out.println("client output...."+msg);
        ctx.writeAndFlush("fromclient1");

    }

    //  当被通知 Channel 是活跃的时候,发 送一条消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(".....  clinet   channelActive........ ");
        ctx.writeAndFlush("22");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }

}
