package com.piter.importedcars.rest.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "cepik.webservice")
@Getter
@Setter
public class CepikWebserviceConfig {

  private int connectionTimeout;
  private String url;

  @Bean
  public ClientHttpRequestFactory getClientHttpRequestFactory() {
    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(connectionTimeout)
        .setConnectionRequestTimeout(connectionTimeout)
        .setSocketTimeout(connectionTimeout)
        .build();

    CloseableHttpClient client = HttpClientBuilder
        .create()
        .setDefaultRequestConfig(config)
        .build();

    return new HttpComponentsClientHttpRequestFactory(client);
  }

  @Bean
  public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
    return new RestTemplate(clientHttpRequestFactory);
  }
}
