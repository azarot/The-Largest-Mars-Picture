package com.example.thelargestmarspicture;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
public class TheLargestMarsPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheLargestMarsPictureApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        var restTemplate =  new RestTemplate();
        var factory = new HttpComponentsClientHttpRequestFactory();
        var httpClient = HttpClientBuilder.create()
          .setRedirectStrategy(new LaxRedirectStrategy())
          .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);

        return restTemplate;
    }
}
