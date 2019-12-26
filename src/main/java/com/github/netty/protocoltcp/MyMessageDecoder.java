package com.github.netty.protocoltcp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/**
 * 功能描述:  自定义解码器
 * @author: qinxuewu
 * @date: 2019/12/26 14:21
 * @since 1.0.0
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //需要将得到二进制字节码-> MessageProtocol 数据包(对象)
        int len=in.readInt();
        byte[] content=new byte[len];
        in.readBytes(content);

        //封装成 MessageProtocol 对象，放入 out， 传递下一个handler业务处理
        MessageProtocol messageProtocol=new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
