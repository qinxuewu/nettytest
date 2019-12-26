package com.github.netty.test6;

import io.netty.channel.Channel;

import java.util.UUID;

public class ImClientApp {
    public static final String HOST = "127.0.0.1";
    public static int PORT = 8899;
    public static void main(String[] args) {
        Channel channel = new ImConnection().connect(HOST, PORT);
        String id = UUID.randomUUID().toString().replaceAll("-", "");
//        // protobuf
//        MessageProto.Message message = MessageProto.Message.newBuilder().setId(id).setContent("hello yinjihuan").build();
//        channel.writeAndFlush(message);
        //对象传输数据
		Message message = new Message();
		message.setId(id);
		message.setContent("hello yinjihuan");
		channel.writeAndFlush(message);
        //字符串传输数据
        //channel.writeAndFlush("yinjihuan");
    }
}
