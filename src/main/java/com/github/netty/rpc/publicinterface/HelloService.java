package com.github.netty.rpc.publicinterface;


/**
 * 功能描述:  公共接口 服务提供方和 服务消费方都需要  一般是一个公共的jar的形式引入
 * @author: qinxuewu
 * @date: 2020/1/2 10:12
 * @since 1.0.0 
 */
public interface HelloService {
    String hello(String mes);
}
