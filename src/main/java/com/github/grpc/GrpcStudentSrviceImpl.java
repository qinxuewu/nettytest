package com.github.grpc;
import com.github.proto.GrpcStudentProtoServiceGrpc;
import com.github.proto.MyRequest;
import com.github.proto.MyResponse;
import io.grpc.stub.StreamObserver;
public class GrpcStudentSrviceImpl  extends GrpcStudentProtoServiceGrpc.GrpcStudentProtoServiceImplBase {

    @Override
    public void getName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收客户端的消息："+request.getName());
        // 每次接下来要做的事
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三11111").build());
        responseObserver.onCompleted(); //结束时调用  返回客户端  只调用一次
    }
}
