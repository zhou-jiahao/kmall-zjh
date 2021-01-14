package com.kgc.kmall.search.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JedisConfig {

    //@Value("${spring.elasticsearch.rest.uris}")


    @Bean
    public JestClient getJestCline() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://192.168.56.138:9200")
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }
}
