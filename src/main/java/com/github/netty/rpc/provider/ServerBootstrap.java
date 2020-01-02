package com.github.netty.rpc.provider;


import com.github.netty.rpc.nettyserver.NettyServer;

/**
 * 功能描述:  服务端的启动类 启动一个Netty服务
 * @author: qinxuewu
 * @date: 2020/1/2 10:14
 * @since 1.0.0
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
