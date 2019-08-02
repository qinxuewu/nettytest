package com.github.test6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 对象解码器
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 11:06
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object obj = ByteUtils.byteToObject(ByteUtils.read(in));
        out.add(obj);
    }

}
