package AstroLab.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.50.2)",
    comments = "Source: astro_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AstroCommandServiceGrpc {

  private AstroCommandServiceGrpc() {}

  public static final String SERVICE_NAME = "AstroLab.AstroCommandService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AstroLab.grpc.ClientRequestProto,
      AstroLab.grpc.ServerResponseProto> getExecuteCommandMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExecuteCommand",
      requestType = AstroLab.grpc.ClientRequestProto.class,
      responseType = AstroLab.grpc.ServerResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AstroLab.grpc.ClientRequestProto,
      AstroLab.grpc.ServerResponseProto> getExecuteCommandMethod() {
    io.grpc.MethodDescriptor<AstroLab.grpc.ClientRequestProto, AstroLab.grpc.ServerResponseProto> getExecuteCommandMethod;
    if ((getExecuteCommandMethod = AstroCommandServiceGrpc.getExecuteCommandMethod) == null) {
      synchronized (AstroCommandServiceGrpc.class) {
        if ((getExecuteCommandMethod = AstroCommandServiceGrpc.getExecuteCommandMethod) == null) {
          AstroCommandServiceGrpc.getExecuteCommandMethod = getExecuteCommandMethod =
              io.grpc.MethodDescriptor.<AstroLab.grpc.ClientRequestProto, AstroLab.grpc.ServerResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExecuteCommand"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AstroLab.grpc.ClientRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AstroLab.grpc.ServerResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new AstroCommandServiceMethodDescriptorSupplier("ExecuteCommand"))
              .build();
        }
      }
    }
    return getExecuteCommandMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AstroCommandServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AstroCommandServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AstroCommandServiceStub>() {
        @java.lang.Override
        public AstroCommandServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AstroCommandServiceStub(channel, callOptions);
        }
      };
    return AstroCommandServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AstroCommandServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AstroCommandServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AstroCommandServiceBlockingStub>() {
        @java.lang.Override
        public AstroCommandServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AstroCommandServiceBlockingStub(channel, callOptions);
        }
      };
    return AstroCommandServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AstroCommandServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AstroCommandServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AstroCommandServiceFutureStub>() {
        @java.lang.Override
        public AstroCommandServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AstroCommandServiceFutureStub(channel, callOptions);
        }
      };
    return AstroCommandServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AstroCommandServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void executeCommand(AstroLab.grpc.ClientRequestProto request,
        io.grpc.stub.StreamObserver<AstroLab.grpc.ServerResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteCommandMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getExecuteCommandMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                AstroLab.grpc.ClientRequestProto,
                AstroLab.grpc.ServerResponseProto>(
                  this, METHODID_EXECUTE_COMMAND)))
          .build();
    }
  }

  /**
   */
  public static final class AstroCommandServiceStub extends io.grpc.stub.AbstractAsyncStub<AstroCommandServiceStub> {
    private AstroCommandServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AstroCommandServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AstroCommandServiceStub(channel, callOptions);
    }

    /**
     */
    public void executeCommand(AstroLab.grpc.ClientRequestProto request,
        io.grpc.stub.StreamObserver<AstroLab.grpc.ServerResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteCommandMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AstroCommandServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AstroCommandServiceBlockingStub> {
    private AstroCommandServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AstroCommandServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AstroCommandServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public AstroLab.grpc.ServerResponseProto executeCommand(AstroLab.grpc.ClientRequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteCommandMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AstroCommandServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AstroCommandServiceFutureStub> {
    private AstroCommandServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AstroCommandServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AstroCommandServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AstroLab.grpc.ServerResponseProto> executeCommand(
        AstroLab.grpc.ClientRequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteCommandMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_EXECUTE_COMMAND = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AstroCommandServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AstroCommandServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXECUTE_COMMAND:
          serviceImpl.executeCommand((AstroLab.grpc.ClientRequestProto) request,
              (io.grpc.stub.StreamObserver<AstroLab.grpc.ServerResponseProto>) responseObserver);
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

  private static abstract class AstroCommandServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AstroCommandServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AstroLab.grpc.AstroServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AstroCommandService");
    }
  }

  private static final class AstroCommandServiceFileDescriptorSupplier
      extends AstroCommandServiceBaseDescriptorSupplier {
    AstroCommandServiceFileDescriptorSupplier() {}
  }

  private static final class AstroCommandServiceMethodDescriptorSupplier
      extends AstroCommandServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AstroCommandServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (AstroCommandServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AstroCommandServiceFileDescriptorSupplier())
              .addMethod(getExecuteCommandMethod())
              .build();
        }
      }
    }
    return result;
  }
}
