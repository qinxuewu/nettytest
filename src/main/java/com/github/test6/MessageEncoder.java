package com.github.test6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 对象编解码器
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 11:01
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
        byte[] datas = ByteUtils.objectToByte(message);
        out.writeBytes(datas);
        ctx.flush();
    }

}
