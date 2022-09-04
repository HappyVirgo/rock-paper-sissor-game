package com.al.qdt.rps.cmd.infrastructure.config;

import io.grpc.inprocess.InProcessChannelBuilder;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.channelfactory.InProcessChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;
import net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import net.devh.boot.grpc.server.serverfactory.GrpcServerFactory;
import net.devh.boot.grpc.server.serverfactory.InProcessGrpcServerFactory;
import net.devh.boot.grpc.server.service.GrpcServiceDiscoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({GrpcCommonCodecAutoConfiguration.class,
        GrpcServerAutoConfiguration.class, // Create required server beans
        GrpcServerFactoryAutoConfiguration.class, // Select server implementation
        GrpcServerSecurityAutoConfiguration.class,
        GrpcClientAutoConfiguration.class}) // Support @GrpcClient annotation
public class GrpcInProcessConfig {
    @Value("${grpc.server.inProcessName}")
    String channelName;

    @Bean
    GrpcChannelFactory grpcChannelFactory(GrpcChannelsProperties properties,
                                          GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
        return new InProcessChannelFactory(properties, globalClientInterceptorRegistry) {
            @Override
            protected InProcessChannelBuilder newChannelBuilder(String name) {
                return super.newChannelBuilder(channelName); // use fixed inMemory channel name: test
            }
        };
    }

    @Bean
    GrpcServerFactory grpcServerFactory(GrpcServerProperties properties,
                                        GrpcServiceDiscoverer discoverer) {
        final var factory = new InProcessGrpcServerFactory(channelName, properties);
        discoverer.findGrpcServices().forEach(factory::addService);
        return factory;
    }
}
