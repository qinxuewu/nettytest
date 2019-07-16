package com.github.protobuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 自定义处理器
 *
 * @author qinxuewu
 * @create 19/7/16下午9:32
 * @since 1.0.0
 */


public class ProtoServerHander extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) {
        MyDataInfo.MyMessage.DataType type=msg.getDataType();
        System.out.println("服务端接收自定义protobuf消息:"+ctx.channel().remoteAddress()+" type="+type);

        if(type == MyDataInfo.MyMessage.DataType.PersonType){
            MyDataInfo.Person person=msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getAddress());
            System.out.println(person.getAge());
        }
        if(type == MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog=msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        }

        if(type == MyDataInfo.MyMessage.DataType.CatType){
            MyDataInfo.Cat cat=msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getCity());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channelActive :"+ctx.channel().remoteAddress());
    }
}
