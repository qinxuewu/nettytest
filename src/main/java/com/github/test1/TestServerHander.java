package com.github.test1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;



/**
 * 〈〉
 *
 * @author qinxuewu
 * @create 19/7/12上午8:21
 * @since 1.0.0
 */

public class TestServerHander extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws  Exception{
            if (msg instanceof HttpRequest){
                System.err.println("【服务端接收的请求。。。。。。。】");
                ByteBuf conntent=Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
                FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,conntent);

                response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH,conntent.readableBytes());
                ctx.writeAndFlush(response); // 返回给客户端

            }
    }
}
