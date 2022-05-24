package com.al.qdt.common.config;

import com.al.qdt.common.infrastructure.commands.CommandDispatcherImpl;
import com.al.qdt.common.infrastructure.commands.EventSourcingHandlerImpl;
import com.al.qdt.common.infrastructure.commands.EventStoreImpl;
import com.al.qdt.common.producers.EventProducerImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {EventSourcingHandlerImpl.class, EventStoreImpl.class,
        CommandDispatcherImpl.class, EventProducerImpl.class})
public class AppCmdConfig {
}
