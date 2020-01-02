package com.github.netty.rpc.customer;


import com.github.netty.rpc.nettyserver.NettyClient;
import com.github.netty.rpc.publicinterface.HelloService;

/**
 * 功能描述:  客户端自动
 * @author: qinxuewu
 * @date: 2020/1/2 10:14
 * @since 1.0.0
 */
public class ClientBootstrap {
    //这里定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws Exception {
        //创建一个消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        for (;; ) {
            Thread.sleep(2 * 1000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果 res= " + res);
        }
    }
}
