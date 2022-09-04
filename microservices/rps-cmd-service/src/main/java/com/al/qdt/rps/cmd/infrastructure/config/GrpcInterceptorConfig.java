package com.al.qdt.rps.cmd.infrastructure.config;

import com.al.qdt.common.infrastructure.grpc.interceptors.LogGrpcInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * gRPC configuration.
 */
@Configuration(proxyBeanMethods = false)
public class GrpcInterceptorConfig {

    @GrpcGlobalServerInterceptor
    public LogGrpcInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }
}
