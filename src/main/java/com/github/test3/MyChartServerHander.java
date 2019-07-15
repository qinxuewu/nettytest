package com.github.test3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *  自定义逻辑处理 hander
 *
 * @author qinxuewu
 * @create 19/7/13下午1:18
 * @since 1.0.0
 */

public class MyChartServerHander  extends SimpleChannelInboundHandler<String> {
    // 存储所有连接的channle 广播使用
  private  static    ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.err.println("server  channelRead0 执行............. channelGroup.size()) "+channelGroup.size());
        Channel channel=ctx.channel();  // 获取当前发送消息的连接
            for(Channel ch:channelGroup){
                //  不相等 当前所遍历的对象不是发消息的客户端
                if(channel== ch){
                    ch.writeAndFlush("[自己]："+msg+ "\n");
                }else{
                    ch.writeAndFlush(channel.remoteAddress()+" 发送了一条消息 :"+msg+ "\n");
                }
            }
    }

    // 新的连接 加入时
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println("server handler  执行...................."+channelGroup.size());
        Channel channel=ctx.channel(); //  获取当前加入的连接
        // 广播
        channelGroup.writeAndFlush("【有新的连接】-"+channel.remoteAddress()+" 加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel(); //  获取当前加入的连接
        channelGroup.writeAndFlush("【连接】"+channel.remoteAddress()+" 离开\n");
//        channelGroup.remove(channel);  可以不写 netty会自动处理
    }


    // 连接活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线了");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        System.out.println(channel.remoteAddress()+" 已下线");
    }

    // 异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
