package com.alinahamatkhanova.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientUtil {

    private final WebClient webClient;

    public ClientUtil(@Value("${banksystem.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    private ResponseEntity<String> sendRequest(HttpMethod method, String path, Object body) {
        WebClient.RequestBodySpec request = webClient.method(method).uri(path);

        if (method == HttpMethod.POST && body != null) {
            request.bodyValue(body);
        }
        return request.retrieve().toEntity(String.class).block();
    }

    public ResponseEntity<String> get(String path) {
        return sendRequest(HttpMethod.GET, path, null);
    }

    public ResponseEntity<String> post(String path, Object body) {
        return sendRequest(HttpMethod.POST, path, body);
    }

    public ResponseEntity<String> postWithoutBody(String path) {
        return sendRequest(HttpMethod.POST, path, null);
    }

    public ResponseEntity<String> delete(String path) {
        return sendRequest(HttpMethod.DELETE, path, null);
    }
}