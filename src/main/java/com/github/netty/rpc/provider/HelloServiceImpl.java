package com.github.netty.rpc.provider;


import com.github.netty.rpc.publicinterface.HelloService;

/**
 * 功能描述: 服务端  服务提供者
 * @author: qinxuewu
 * @date: 2020/1/2 10:12
 * @since 1.0.0
 */
public class HelloServiceImpl implements HelloService {
    private static int count = 0;

    /**
     * 当有消费方调用该方法时， 就返回一个结果
     * @param mes
     * @return
     */
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        //根据mes 返回不同的结果
        if(mes != null) {
            return "你好客户端, 我已经收到你的消息 [" + mes + "] 第" + (++count) + " 次";
        } else {
            return "你好客户端, 我已经收到你的消息 ";
        }
    }
}
