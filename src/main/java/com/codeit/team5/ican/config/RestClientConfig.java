package com.codeit.team5.ican.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String teamId = "junha";

    @Bean
    public RestClient restClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(5)) // 연결 타임아웃
                .setResponseTimeout(Timeout.ofSeconds(5))  // 읽기 타임아웃
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return RestClient.builder()
                .requestFactory(factory)
                .baseUrl("https://sp-slidtodo-api.vercel.app/" + teamId) // 공통 URL 설정
                .defaultHeaders(headers -> headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON)) // 기본 헤더 설정
                .build();
    }
}
