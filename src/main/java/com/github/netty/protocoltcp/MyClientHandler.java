package com.github.netty.protocoltcp;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;

/**
 * 功能描述:  客户端业务处理
 * @author: qinxuewu
 * @date: 2019/12/26 14:32
 * @since 1.0.0
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送5条数据 "今天天气冷，吃火锅" 编号

        for(int i = 0; i< 5; i++) {
            String mes = "今天天气冷，吃火锅";
            byte[] content = mes.getBytes(Charset.forName("utf-8"));
            int length = mes.getBytes(Charset.forName("utf-8")).length;

            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);

        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到消息："+new String(content, Charset.forName("utf-8")));
//        System.out.println("长度=" + len);
        System.out.println("客户端接收消息数量=" + (++this.count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
