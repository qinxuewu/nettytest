package com.github.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcService {
    private Server server;

    private  void  start() throws IOException {
        this.server= ServerBuilder.forPort(8899).addService(new GrpcStudentSrviceImpl()).build().start();
        System.out.println("server startted!..................");

        // 回调钩子
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("jvm关闭！");

            GrpcService.this.stop();
        }));

        System.out.println("执行这里。。。。。。。。。。。。。。。");
    }

    private  void  stop(){
        if (null != this.server){
            this.server.shutdown();
        }
    }

    //  等待被调用
    private void   awaitTermination() throws  InterruptedException{
        if (null != this.server){
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws  Exception {
        GrpcService service=new GrpcService();
        service.start();
        service.awaitTermination();
    }
}
