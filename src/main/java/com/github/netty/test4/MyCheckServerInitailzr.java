package com.github.netty.test4;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**

 *
 * @author qinxuewu
 * @create 19/7/13上午10:23
 * @since 1.0.0
 */


public class MyCheckServerInitailzr extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws  Exception {
        ChannelPipeline pipeline=ch.pipeline();

         // 空闲检测处理器
         // 读空闲：5。 服务端5秒没有读就会超时
         // 写空闲 7 服务端7秒没有写操作  就会超时
         // 读写空闲 10  服务端10秒没有读写就会超时

        pipeline.addLast(new IdleStateHandler(5,7,10,TimeUnit.SECONDS));

        // 自定义处理器
        pipeline.addLast(new MyCheckServerHander());
    }
}

