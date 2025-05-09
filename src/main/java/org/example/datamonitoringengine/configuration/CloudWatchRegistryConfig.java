//package org.example.datamonitoringengine.configuration;
//
//import io.micrometer.cloudwatch2.CloudWatchConfig;
//import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
//import io.micrometer.core.instrument.Clock;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
//
//@Configuration
//public class CloudWatchRegistryConfig {
//
//    @Bean
//    public CloudWatchConfig cloudWatchConfig(Environment env) {
//        return new CloudWatchConfig() {
//            @Override
//            public String namespace() {
//                // read the property directly, same path as in your application.yml
//                return env.getProperty("management.metrics.export.cloudwatch.namespace");
//            }
//            @Override
//            public String get(String key) {
//                // for everything else (step, batch-size, etc.)
//                return env.getProperty("management.metrics.export.cloudwatch." + key);
//            }
//        };
//    }
//
//    @Bean
//    public CloudWatchAsyncClient cloudWatchAsyncClient() {
//        return CloudWatchAsyncClient.builder().build();
//    }
//
//    @Bean
//    public CloudWatchMeterRegistry cloudWatchMeterRegistry(
//            CloudWatchConfig config,
//            Clock clock,
//            CloudWatchAsyncClient client) {
//        return new CloudWatchMeterRegistry(config, clock, client);
//    }
//}
//
