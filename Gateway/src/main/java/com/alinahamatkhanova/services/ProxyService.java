package com.alinahamatkhanova.services;
import com.alinahamatkhanova.utils.ClientUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ProxyService {

    private final ClientUtil clientUtil;

    public ProxyService(ClientUtil clientUtil) {
        this.clientUtil = clientUtil;
    }

    public ResponseEntity<String> get(String path, Map<String, String> headers) {
        return clientUtil.forwardGet(path, headers);
    }

    public ResponseEntity<String> post(String path, String body, Map<String, String> headers) {
        return clientUtil.forwardPost(path, headers, body);
    }

    public ResponseEntity<String> postWithoutBody(String path, Map<String, String> headers) {
        return clientUtil.forwardPostWithoutBody(path, headers);
    }

    public ResponseEntity<String> delete(String path, Map<String, String> headers) {
        return clientUtil.forwardDelete(path, headers);
    }
}