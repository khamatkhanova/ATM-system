package com.alinahamatkhanova.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class ClientUtil {

    private final WebClient webClient;

    public ClientUtil(@Value("${banksystem.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ResponseEntity<String> forwardGet(String path, Map<String, String> headers) {
        return webClient.get().uri(path).headers(httpHeaders -> headers.forEach(httpHeaders::add)).retrieve().toEntity(String.class).block();
    }

    public ResponseEntity<String> forwardPost(String path, Map<String, String> headers, Object body) {
        return webClient.post().uri(path).headers(httpHeaders -> headers.forEach(httpHeaders::add)).bodyValue(body).retrieve().toEntity(String.class).block();
    }
    public ResponseEntity<String> forwardPostWithoutBody(String path, Map<String, String> headers) {
        return webClient.post().uri(path).headers(httpHeaders -> headers.forEach(httpHeaders::add)).retrieve().toEntity(String.class).block();
    }

    public ResponseEntity<String> forwardDelete(String path, Map<String, String> headers) {
        return webClient.delete().uri(path).headers(httpHeaders -> headers.forEach(httpHeaders::add)).retrieve().toEntity(String.class).block();
    }
}