package com.github.netty.test4;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author qinxuewu
 * @create 19/7/13上午10:22
 * @since 1.0.0
 */


public class MyCheckServerHander extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 强转
        if( evt instanceof IdleStateEvent){
            IdleStateEvent event=(IdleStateEvent)evt;
            String eventType=null;
            //判断事件
            switch (event.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时事件"+eventType);
            ctx.channel().close();
        }
    }
}
