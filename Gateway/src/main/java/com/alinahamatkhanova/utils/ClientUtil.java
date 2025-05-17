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

    private ResponseEntity<String> sendRequest(HttpMethod method, String path, Map<String, String> headers, Object body) {
        WebClient.RequestBodySpec request = webClient.method(method).uri(path).headers(httpHeaders -> headers.forEach(httpHeaders::add));

        if (method == HttpMethod.POST && body != null) {
            request.bodyValue(body);
        }
        return request.retrieve().toEntity(String.class).block();
    }

    public ResponseEntity<String> forwardGet(String path, Map<String, String> headers) {
        return sendRequest(HttpMethod.GET, path, headers, null);
    }

    public ResponseEntity<String> forwardPost(String path, Map<String, String> headers, Object body) {
        return sendRequest(HttpMethod.POST, path, headers, body);
    }

    public ResponseEntity<String> forwardPostWithoutBody(String path, Map<String, String> headers) {
        return sendRequest(HttpMethod.POST, path, headers, null);
    }

    public ResponseEntity<String> forwardDelete(String path, Map<String, String> headers) {
        return sendRequest(HttpMethod.DELETE, path, headers, null);
    }
}