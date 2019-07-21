package com.github.proto;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.1)",
    comments = "Source: src/main/proto/GrpcStudend.porto")
public final class GrpcStudentProtoServiceGrpc {

  private GrpcStudentProtoServiceGrpc() {}

  public static final String SERVICE_NAME = "com.github.proto.GrpcStudentProtoService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.proto.MyRequest,
      com.github.proto.MyResponse> METHOD_GET_NAME =
      io.grpc.MethodDescriptor.<com.github.proto.MyRequest, com.github.proto.MyResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.github.proto.GrpcStudentProtoService", "GetName"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.MyRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.MyResponse.getDefaultInstance()))
          .setSchemaDescriptor(new GrpcStudentProtoServiceMethodDescriptorSupplier("GetName"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.proto.StudentRequest,
      com.github.proto.StudentResponse> METHOD_GET_STUNDET_BY_AGE =
      io.grpc.MethodDescriptor.<com.github.proto.StudentRequest, com.github.proto.StudentResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.github.proto.GrpcStudentProtoService", "GetStundetByAge"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.StudentRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.StudentResponse.getDefaultInstance()))
          .setSchemaDescriptor(new GrpcStudentProtoServiceMethodDescriptorSupplier("GetStundetByAge"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.proto.StudentRequest,
      com.github.proto.StudentResponseList> METHOD_GET_STUDENTS_WEAPPER_BYAGE =
      io.grpc.MethodDescriptor.<com.github.proto.StudentRequest, com.github.proto.StudentResponseList>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.github.proto.GrpcStudentProtoService", "GetStudentsWeapperByage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.StudentRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.StudentResponseList.getDefaultInstance()))
          .setSchemaDescriptor(new GrpcStudentProtoServiceMethodDescriptorSupplier("GetStudentsWeapperByage"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.proto.StreamRequest,
      com.github.proto.StreamResponse> METHOD_BI_TALK =
      io.grpc.MethodDescriptor.<com.github.proto.StreamRequest, com.github.proto.StreamResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.github.proto.GrpcStudentProtoService", "BiTalk"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.StreamRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.proto.StreamResponse.getDefaultInstance()))
          .setSchemaDescriptor(new GrpcStudentProtoServiceMethodDescriptorSupplier("BiTalk"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GrpcStudentProtoServiceStub newStub(io.grpc.Channel channel) {
    return new GrpcStudentProtoServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GrpcStudentProtoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GrpcStudentProtoServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GrpcStudentProtoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GrpcStudentProtoServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class GrpcStudentProtoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getName(com.github.proto.MyRequest request,
        io.grpc.stub.StreamObserver<com.github.proto.MyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_NAME, responseObserver);
    }

    /**
     */
    public void getStundetByAge(com.github.proto.StudentRequest request,
        io.grpc.stub.StreamObserver<com.github.proto.StudentResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_STUNDET_BY_AGE, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.github.proto.StudentRequest> getStudentsWeapperByage(
        io.grpc.stub.StreamObserver<com.github.proto.StudentResponseList> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_GET_STUDENTS_WEAPPER_BYAGE, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.github.proto.StreamRequest> biTalk(
        io.grpc.stub.StreamObserver<com.github.proto.StreamResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_BI_TALK, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_NAME,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.proto.MyRequest,
                com.github.proto.MyResponse>(
                  this, METHODID_GET_NAME)))
          .addMethod(
            METHOD_GET_STUNDET_BY_AGE,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.github.proto.StudentRequest,
                com.github.proto.StudentResponse>(
                  this, METHODID_GET_STUNDET_BY_AGE)))
          .addMethod(
            METHOD_GET_STUDENTS_WEAPPER_BYAGE,
            asyncClientStreamingCall(
              new MethodHandlers<
                com.github.proto.StudentRequest,
                com.github.proto.StudentResponseList>(
                  this, METHODID_GET_STUDENTS_WEAPPER_BYAGE)))
          .addMethod(
            METHOD_BI_TALK,
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.github.proto.StreamRequest,
                com.github.proto.StreamResponse>(
                  this, METHODID_BI_TALK)))
          .build();
    }
  }

  /**
   */
  public static final class GrpcStudentProtoServiceStub extends io.grpc.stub.AbstractStub<GrpcStudentProtoServiceStub> {
    private GrpcStudentProtoServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcStudentProtoServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcStudentProtoServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcStudentProtoServiceStub(channel, callOptions);
    }

    /**
     */
    public void getName(com.github.proto.MyRequest request,
        io.grpc.stub.StreamObserver<com.github.proto.MyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_NAME, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStundetByAge(com.github.proto.StudentRequest request,
        io.grpc.stub.StreamObserver<com.github.proto.StudentResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_STUNDET_BY_AGE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.github.proto.StudentRequest> getStudentsWeapperByage(
        io.grpc.stub.StreamObserver<com.github.proto.StudentResponseList> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_GET_STUDENTS_WEAPPER_BYAGE, getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.github.proto.StreamRequest> biTalk(
        io.grpc.stub.StreamObserver<com.github.proto.StreamResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_BI_TALK, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class GrpcStudentProtoServiceBlockingStub extends io.grpc.stub.AbstractStub<GrpcStudentProtoServiceBlockingStub> {
    private GrpcStudentProtoServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcStudentProtoServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcStudentProtoServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcStudentProtoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.proto.MyResponse getName(com.github.proto.MyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_NAME, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.github.proto.StudentResponse> getStundetByAge(
        com.github.proto.StudentRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_STUNDET_BY_AGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GrpcStudentProtoServiceFutureStub extends io.grpc.stub.AbstractStub<GrpcStudentProtoServiceFutureStub> {
    private GrpcStudentProtoServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcStudentProtoServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcStudentProtoServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcStudentProtoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.proto.MyResponse> getName(
        com.github.proto.MyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_NAME, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_NAME = 0;
  private static final int METHODID_GET_STUNDET_BY_AGE = 1;
  private static final int METHODID_GET_STUDENTS_WEAPPER_BYAGE = 2;
  private static final int METHODID_BI_TALK = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GrpcStudentProtoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GrpcStudentProtoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_NAME:
          serviceImpl.getName((com.github.proto.MyRequest) request,
              (io.grpc.stub.StreamObserver<com.github.proto.MyResponse>) responseObserver);
          break;
        case METHODID_GET_STUNDET_BY_AGE:
          serviceImpl.getStundetByAge((com.github.proto.StudentRequest) request,
              (io.grpc.stub.StreamObserver<com.github.proto.StudentResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STUDENTS_WEAPPER_BYAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getStudentsWeapperByage(
              (io.grpc.stub.StreamObserver<com.github.proto.StudentResponseList>) responseObserver);
        case METHODID_BI_TALK:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.biTalk(
              (io.grpc.stub.StreamObserver<com.github.proto.StreamResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GrpcStudentProtoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GrpcStudentProtoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.proto.GrpcStudentProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GrpcStudentProtoService");
    }
  }

  private static final class GrpcStudentProtoServiceFileDescriptorSupplier
      extends GrpcStudentProtoServiceBaseDescriptorSupplier {
    GrpcStudentProtoServiceFileDescriptorSupplier() {}
  }

  private static final class GrpcStudentProtoServiceMethodDescriptorSupplier
      extends GrpcStudentProtoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GrpcStudentProtoServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GrpcStudentProtoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GrpcStudentProtoServiceFileDescriptorSupplier())
              .addMethod(METHOD_GET_NAME)
              .addMethod(METHOD_GET_STUNDET_BY_AGE)
              .addMethod(METHOD_GET_STUDENTS_WEAPPER_BYAGE)
              .addMethod(METHOD_BI_TALK)
              .build();
        }
      }
    }
    return result;
  }
}
