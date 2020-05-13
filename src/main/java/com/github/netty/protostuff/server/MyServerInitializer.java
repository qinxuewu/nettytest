package com.github.netty.protostuff.server;
import com.github.netty.protostuff.bean.UserInfo;
import com.github.netty.protostuff.codec.ObjDecoder;
import com.github.netty.protostuff.codec.ObjEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjDecoder(UserInfo.class)); //加入解码器
        pipeline.addLast(new ObjEncoder(UserInfo.class)); //加入编码器
        pipeline.addLast(new MyServerHandler());
    }
}
