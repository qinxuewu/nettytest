package com.github.netty.protostuff.client;
import com.github.netty.protocoltcp.MessageProtocol;
import com.github.netty.protostuff.bean.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.alibaba.fastjson.JSON;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:  客户端业务处理
 * @author: qinxuewu
 * @date: 2019/12/26 14:32
 * @since 1.0.0
 */

/**
 * 一般用netty来发送和接收数据都会继承SimpleChannelInboundHandler和ChannelInboundHandlerAdapter这两个抽象类这两个到底有什么区别呢？
 *
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Object> {

    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送5条数据 "今天天气冷，吃火锅" 编号
        System.out.println("本客户端链接到服务端。channelId：" + ctx.channel().id());
        System.out.println("开始发送5条消息给服务端........");
        for(int i = 0; i< 5; i++) {
            String mes = "今天天气冷，吃火锅 第"+i+"次";
            //发送消息给服务端
            ctx.writeAndFlush(new UserInfo(ctx.channel().id().toString(),"张三说"+mes));
        }
    }

    /**
     * 客户端接收消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,Object msg) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息类型：" + msg.getClass());
        System.out.println("接收到消息内容：" + JSON.toJSONString(msg));
        System.out.println("客户端接收消息数量=" + (++this.count));

    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开链接" + ctx.channel().localAddress().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
