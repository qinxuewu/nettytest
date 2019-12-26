package com.github.netty.test5;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

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

        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // http聚合处理器 , 多个块集合一起发送
        pipeline.addLast(new HttpObjectAggregator(8192));


        // ====================== 增加心跳支持 start    ======================
        // 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
        // 如果是读空闲或者写空闲，不处理
        pipeline.addLast(new IdleStateHandler(8, 10, 12));



        // websocker处理器
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义处理器
        pipeline.addLast(new TextWebScokerFramerHander());
    }
}
