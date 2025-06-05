package AstroLab.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.50.2)",
    comments = "Source: astro_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AstroAuthServiceGrpc {

  private AstroAuthServiceGrpc() {}

  public static final String SERVICE_NAME = "AstroLab.AstroAuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AstroLab.grpc.UserDTOProto,
      AstroLab.grpc.AuthResponseProto> getAuthenticateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Authenticate",
      requestType = AstroLab.grpc.UserDTOProto.class,
      responseType = AstroLab.grpc.AuthResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AstroLab.grpc.UserDTOProto,
      AstroLab.grpc.AuthResponseProto> getAuthenticateMethod() {
    io.grpc.MethodDescriptor<AstroLab.grpc.UserDTOProto, AstroLab.grpc.AuthResponseProto> getAuthenticateMethod;
    if ((getAuthenticateMethod = AstroAuthServiceGrpc.getAuthenticateMethod) == null) {
      synchronized (AstroAuthServiceGrpc.class) {
        if ((getAuthenticateMethod = AstroAuthServiceGrpc.getAuthenticateMethod) == null) {
          AstroAuthServiceGrpc.getAuthenticateMethod = getAuthenticateMethod =
              io.grpc.MethodDescriptor.<AstroLab.grpc.UserDTOProto, AstroLab.grpc.AuthResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Authenticate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AstroLab.grpc.UserDTOProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AstroLab.grpc.AuthResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new AstroAuthServiceMethodDescriptorSupplier("Authenticate"))
              .build();
        }
      }
    }
    return getAuthenticateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AstroAuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AstroAuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AstroAuthServiceStub>() {
        @java.lang.Override
        public AstroAuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AstroAuthServiceStub(channel, callOptions);
        }
      };
    return AstroAuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AstroAuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AstroAuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AstroAuthServiceBlockingStub>() {
        @java.lang.Override
        public AstroAuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AstroAuthServiceBlockingStub(channel, callOptions);
        }
      };
    return AstroAuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AstroAuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AstroAuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AstroAuthServiceFutureStub>() {
        @java.lang.Override
        public AstroAuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AstroAuthServiceFutureStub(channel, callOptions);
        }
      };
    return AstroAuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AstroAuthServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void authenticate(AstroLab.grpc.UserDTOProto request,
        io.grpc.stub.StreamObserver<AstroLab.grpc.AuthResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthenticateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthenticateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                AstroLab.grpc.UserDTOProto,
                AstroLab.grpc.AuthResponseProto>(
                  this, METHODID_AUTHENTICATE)))
          .build();
    }
  }

  /**
   */
  public static final class AstroAuthServiceStub extends io.grpc.stub.AbstractAsyncStub<AstroAuthServiceStub> {
    private AstroAuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AstroAuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AstroAuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticate(AstroLab.grpc.UserDTOProto request,
        io.grpc.stub.StreamObserver<AstroLab.grpc.AuthResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AstroAuthServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AstroAuthServiceBlockingStub> {
    private AstroAuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AstroAuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AstroAuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public AstroLab.grpc.AuthResponseProto authenticate(AstroLab.grpc.UserDTOProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthenticateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AstroAuthServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AstroAuthServiceFutureStub> {
    private AstroAuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AstroAuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AstroAuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AstroLab.grpc.AuthResponseProto> authenticate(
        AstroLab.grpc.UserDTOProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AstroAuthServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AstroAuthServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE:
          serviceImpl.authenticate((AstroLab.grpc.UserDTOProto) request,
              (io.grpc.stub.StreamObserver<AstroLab.grpc.AuthResponseProto>) responseObserver);
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

  private static abstract class AstroAuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AstroAuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return AstroLab.grpc.AstroServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AstroAuthService");
    }
  }

  private static final class AstroAuthServiceFileDescriptorSupplier
      extends AstroAuthServiceBaseDescriptorSupplier {
    AstroAuthServiceFileDescriptorSupplier() {}
  }

  private static final class AstroAuthServiceMethodDescriptorSupplier
      extends AstroAuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AstroAuthServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (AstroAuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AstroAuthServiceFileDescriptorSupplier())
              .addMethod(getAuthenticateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
