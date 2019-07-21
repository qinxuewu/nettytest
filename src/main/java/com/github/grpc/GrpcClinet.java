package com.github.grpc;

import com.github.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/***
 *  三种发送数据模式：
 *   客户端请求 服务端响应模式
 *   客户端发送一个流式的数据，服务端返回一个普通的响应
 *   客户端发送流式的数据 服务端返回流式的数据
 */
public class GrpcClinet {

    public static void main(String[] args) throws  Exception {
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",8899)
                .usePlaintext().build();
        GrpcStudentProtoServiceGrpc.GrpcStudentProtoServiceBlockingStub blockingStub=GrpcStudentProtoServiceGrpc
                .newBlockingStub(managedChannel);

        System.out.println("------------------ 客户端请求 服务端响应模式数据演示---------------------");
        // 异步
        GrpcStudentProtoServiceGrpc.GrpcStudentProtoServiceStub stub=GrpcStudentProtoServiceGrpc.newStub(managedChannel);
        // 发送数据
        MyResponse myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());
        myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());
        myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());
        myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());


        // 接受服务端返回的数据
        System.out.println(myResponse.getRealname());

        System.out.println("------------------客户端请求，接收服务端返回的流式数据演示---------------------");

       Iterator<StudentResponse> iterator=
               blockingStub.getStundetByAge(StudentRequest.newBuilder().setAge(10).build());

       while (iterator.hasNext()){
           StudentResponse response=iterator.next();
           System.out.println(response.getName()+","+response.getCity());
       }


        System.out.println("------------------客户端发流式数据,接收一个返回集合数据---------------------");

        StreamObserver<StudentResponseList> studentResponseListStreamObserver=new StreamObserver<StudentResponseList>(){
            @Override
            public void onNext(StudentResponseList studentResponseList) {
                studentResponseList.getStudentResponseList().forEach(response->{
                    System.out.println(response.getName()+","+response.getCity());

                });
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };

        //  客户端只要以流式发送请求  都是异步的进行调用
        StreamObserver<StudentRequest>  studentRequestStreamObserver= stub.getStudentsWeapperByage(studentResponseListStreamObserver);
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());

        studentRequestStreamObserver.onCompleted();


        //  双向流式数据通信
        StreamObserver<StreamRequest> streamRequestStreamObserver=stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse streamResponse) {
                System.out.println("双向流式数据通信， 接收服务端返回数据："+streamResponse.getRequestInfo());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });

        // 给客户端发送流式数据
        for (int i = 0; i <10 ; i++) {
            streamRequestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(System.currentTimeMillis()+"---"+i).build());

            Thread.sleep(1000);
        }
        managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }
}
