package com.github.grpc;
import com.github.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class GrpcStudentSrviceImpl  extends GrpcStudentProtoServiceGrpc.GrpcStudentProtoServiceImplBase {

    @Override
    public void getName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("--------接收客户端的消息："+request.getName());
        // 每次接下来要做的事
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三11111").build());
        System.out.println("---------返回流数据给客户端----------");
        responseObserver.onCompleted(); //结束时调用  返回客户端  只调用一次
    }


    // 接收流失数据（一个集合）

    @Override
    public void getStundetByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("-------------接收客户端消息 ："+request.getAge());

        System.out.println("---------  返回流式数据给客户端  ----------");

        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan").setCity("广州").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan11").setCity("广州11").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan22").setCity("广州22").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan33").setCity("广州33").build());

        // 表示服务端处理数据完成
        responseObserver.onCompleted();
    }

    // 双向流式数据通信
    @Override
    public StreamObserver<StudentRequest> getStudentsWeapperByage(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest studentRequest) {
                System.out.println("----------  接收客户端流式消息："+ studentRequest.getAge());

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            // 服务端接收所有流式数据  结束时调用
            @Override
            public void onCompleted() {
                System.out.println("----------- 返回一个集合数据给客户端：------------");

                StudentResponse studentResponse=StudentResponse.newBuilder().setName("66666").setCity("guagnzhou").build();
                StudentResponse studentResponse2=StudentResponse.newBuilder().setName("7777").setCity("guagnzhou7").build();
                StudentResponse studentResponse3=StudentResponse.newBuilder().setName("8888").setCity("guagnzhou8").build();

                StudentResponseList studentResponseList=StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse).addStudentResponse(studentResponse2)
                        .addStudentResponse(studentResponse3).build();

                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();


            }
        };
    }

    // 双向流失数据通信
    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>(){
            @Override
            public void onNext(StreamRequest streamRequest) {
                System.out.println("--------- 双向流式数据通信------ "+streamRequest.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setRequestInfo(UUID.randomUUID().toString()).build());

            }

            @Override
            public void onError(Throwable throwable) {
                    System.out.println(throwable.getMessage());
            }

            // 接收客户端所有流式数据后调用
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
