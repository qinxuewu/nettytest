package com.github.grpc;

import com.github.proto.GrpcStudentProtoServiceGrpc;
import com.github.proto.MyRequest;
import com.github.proto.MyResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GrpcClinet {

    public static void main(String[] args) throws  Exception {
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",8899)
                .usePlaintext().build();
        GrpcStudentProtoServiceGrpc.GrpcStudentProtoServiceBlockingStub blockingStub=GrpcStudentProtoServiceGrpc
                .newBlockingStub(managedChannel);

        MyResponse myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());
        myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());
        myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());
        myResponse=blockingStub.getName(MyRequest.newBuilder().setName("zhangsan").build());


        System.out.println(myResponse.getRealname());
        managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }
}
