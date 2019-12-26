package com.github.netty.test1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * netty实现的http服务    初始化处理器
 *
 * @author qinxuewu
 * @create 19/7/12上午8:21
 * @since 1.0.0
 */
public class TestServerlnitailzr extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline=ch.pipeline();
        // 添加netty内置的处理器和自定义的多个处理器
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("testServerHander",new TestServerHander());
    }
}
