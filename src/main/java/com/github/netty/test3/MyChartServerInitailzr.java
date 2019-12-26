package com.github.netty.test3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 *  初始化器
 *
 * @author qinxuewu
 * @create 19/7/13下午1:18
 * @since 1.0.0
 */


public class MyChartServerInitailzr  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline=ch.pipeline();
        // netty内置解码器  根据分割符解码 \r,\n 等等
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096,Delimiters.lineDelimiter()));
        // 字符串解码器
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

        // 自定义hander
        pipeline.addLast(new MyChartServerHander());
    }
}
