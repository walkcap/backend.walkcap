package com.walkcap.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.walkcap.zuul.client.AccessRestTemplate;
import com.walkcap.zuul.filter.AccessPreFilter;

/**
 * 
 * http://localhost:8769/api-b/hello?name=shoen
 * 
 * @author shoen
 *
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class ZuulApplication {
	// ribbon
    @Bean(name="accessRestTemplate")
    @LoadBalanced
    RestTemplate restTemplate() {
        return new AccessRestTemplate();
    }

    // 在主类中，开启前面已经定义的过滤器
    @Bean
    AccessPreFilter accessFilter(){
        return new AccessPreFilter();
    }
    
    public static void main(String[] args) {
    	SpringApplication.run( ZuulApplication.class, args );
    }
}
