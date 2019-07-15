package com.github.test5;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器
 * @author qxw
 * @version 1.00
 * @time  15/7/2019 下午 1:02
 */
public class MyWebScokerServerInitailzr extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline=ch.pipeline();
        // webscoker也是基于http的 使用http编解码器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        // http聚合处理器 , 多个块集合一起发送
        pipeline.addLast(new HttpObjectAggregator(8192));
        // websocker处理器
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义处理器
        pipeline.addLast(new TextWebScokerFramerHander());
    }
}
