package com.PanikaSos.PS_springB.request;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${service.security.secure-key.user}") String username,
            @Value("${service.security.secure-key.password}") String password
    ){
        return new BasicAuthRequestInterceptor(username,password);
    }
}
