package com.github.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 *
 * @author qinxuewu
 * @create 19/7/16下午10:01
 * @since 1.0.0
 */


public class ProtoClientHander  extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) {

    }

    // 活动状态就向服务端发送消息
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.err.println("活动状态就向服务端发送消息:"+ctx.channel().remoteAddress());
//        // 构造对象 初始化数据
//        DataInfo.Person person=  DataInfo.Person.newBuilder()
//                .setName("qxw").setAge(25).setAddress("广州").build();
//        ctx.writeAndFlush(person);
//    }

    // 活动状态就向服务端发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("活动状态就向服务端发送消息:"+ctx.channel().remoteAddress());
        int index= new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;
         if (0 == index){
              myMessage=MyDataInfo.MyMessage.newBuilder()
                     .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                     .setPerson(MyDataInfo.Person.newBuilder().setName("qxw").setAge(25).setAddress("广州"))
                     .build();
         }else if(1 == index){
              myMessage=MyDataInfo.MyMessage.newBuilder()
                     .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                     .setDog(MyDataInfo.Dog.newBuilder().setName("dogdog").setAge(25))
                     .build();

         }else {
           myMessage=MyDataInfo.MyMessage.newBuilder()
                     .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                     .setCat(MyDataInfo.Cat.newBuilder().setName("dogdog").setCity("wuhan"))
                     .build();
         }
         ctx.channel().writeAndFlush(myMessage);
    }
}
