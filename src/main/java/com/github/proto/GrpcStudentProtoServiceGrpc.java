package com.github.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.10.0)",
    comments = "Source: src/main/proto/GrpcStudend.porto")
public final class GrpcStudentProtoServiceGrpc {

  private GrpcStudentProtoServiceGrpc() {}

  public static final String SERVICE_NAME = "com.github.proto.GrpcStudentProtoService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetNameMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.github.proto.MyRequest,
      com.github.proto.MyResponse> METHOD_GET_NAME = getGetNameMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.github.proto.MyRequest,
      com.github.proto.MyResponse> getGetNameMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.github.proto.MyRequest,
      com.github.proto.MyResponse> getGetNameMethod() {
    return getGetNameMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.github.proto.MyRequest,
      com.github.proto.MyResponse> getGetNameMethodHelper() {
    io.grpc.MethodDescriptor<com.github.proto.MyRequest, com.github.proto.MyResponse> getGetNameMethod;
    if ((getGetNameMethod = GrpcStudentProtoServiceGrpc.getGetNameMethod) == null) {
      synchronized (GrpcStudentProtoServiceGrpc.class) {
        if ((getGetNameMethod = GrpcStudentProtoServiceGrpc.getGetNameMethod) == null) {
          GrpcStudentProtoServiceGrpc.getGetNameMethod = getGetNameMethod = 
              io.grpc.MethodDescriptor.<com.github.proto.MyRequest, com.github.proto.MyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.github.proto.GrpcStudentProtoService", "GetName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.proto.MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.proto.MyResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GrpcStudentProtoServiceMethodDescriptorSupplier("GetName"))
                  .build();
          }
        }
     }
     return getGetNameMethod;
  }

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
      asyncUnimplementedUnaryCall(getGetNameMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetNameMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.proto.MyRequest,
                com.github.proto.MyResponse>(
                  this, METHODID_GET_NAME)))
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
          getChannel().newCall(getGetNameMethodHelper(), getCallOptions()), request, responseObserver);
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
          getChannel(), getGetNameMethodHelper(), getCallOptions(), request);
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
          getChannel().newCall(getGetNameMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_NAME = 0;

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
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
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
              .addMethod(getGetNameMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
