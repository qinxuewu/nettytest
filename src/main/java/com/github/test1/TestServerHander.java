package com.github.test1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;



/**
 * http服务
 *
 * @author qinxuewu
 * @create 19/7/12上午8:21
 * @since 1.0.0
 */

public class TestServerHander extends SimpleChannelInboundHandler<HttpObject> {




    //  1
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println("handlerAdded  新的通道添加时.........");
        super.handlerAdded(ctx);
    }

    //  2
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channelRegistered .........");
        super.channelRegistered(ctx);
    }

    //  3
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channelActive  处于活动状态时请求 .........");
        super.channelActive(ctx);
    }

    //  4
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws  Exception{
        if (msg instanceof HttpRequest){

            HttpRequest httpRequest=(HttpRequest) msg;
            System.err.println("请求方法名："+httpRequest.method().name());

            System.err.println("【服务端接收的请求。。。。。。。】");
            ByteBuf conntent=Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,conntent);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,conntent.readableBytes());
            ctx.writeAndFlush(response); // 返回给客户端

        }
    }

    //  5

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channelInactive  不处处于活动状态时请求 .........");
        super.channelInactive(ctx);
    }


    //  6
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channelUnregistered   .........");
        super.channelUnregistered(ctx);
    }

    //  发生异常时
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("exceptionCaught  发生异常时 .........");
        super.exceptionCaught(ctx, cause);
    }


}
