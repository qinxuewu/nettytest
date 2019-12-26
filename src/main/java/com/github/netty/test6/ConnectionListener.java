package com.github.netty.test6;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import java.util.concurrent.TimeUnit;

/**
 * 负责监听启动时连接失败，重新连接功能
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 下午 12:09
 */
public class ConnectionListener implements ChannelFutureListener {
    private ImConnection imConnection = new ImConnection();
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            System.out.println("!channelFuture.isSuccess()");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    System.err.println("服务端链接不上，开始重连操作...");
                    imConnection.connect(ImClientApp.HOST, ImClientApp.PORT);
                }
            }, 1L, TimeUnit.SECONDS);
        } else {
            System.err.println("服务端链接成功...");
        }
    }
}