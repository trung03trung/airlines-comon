package com.skyviet.common.client;

import com.skyviet.common.dto.ApiResponse;
import com.skyviet.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
public abstract class BaseRestClient {

    protected final RestClient restClient;
    private final String serviceName;

    protected BaseRestClient(String baseUrl, String serviceName) {
        this.serviceName = serviceName;
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    protected <T> T get(String uri, ParameterizedTypeReference<ApiResponse<T>> responseType, Object... uriVars) {
        try {
            ApiResponse<T> response = restClient.get()
                    .uri(uri, uriVars)
                    .retrieve()
                    .body(responseType);
            return extractData(response);
        } catch (RestClientResponseException ex) {
            throw handleError("GET", uri, ex);
        }
    }

    protected <T> T post(String uri, Object body, ParameterizedTypeReference<ApiResponse<T>> responseType, Object... uriVars) {
        try {
            ApiResponse<T> response = restClient.post()
                    .uri(uri, uriVars)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(responseType);
            return extractData(response);
        } catch (RestClientResponseException ex) {
            throw handleError("POST", uri, ex);
        }
    }

    protected void postVoid(String uri, Object body, Object... uriVars) {
        try {
            restClient.post()
                    .uri(uri, uriVars)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            throw handleError("POST", uri, ex);
        }
    }

    protected void postVoidNoBody(String uri, Object... uriVars) {
        try {
            restClient.post()
                    .uri(uri, uriVars)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            throw handleError("POST", uri, ex);
        }
    }

    protected <T> T put(String uri, Object body, ParameterizedTypeReference<ApiResponse<T>> responseType, Object... uriVars) {
        try {
            ApiResponse<T> response = restClient.put()
                    .uri(uri, uriVars)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(responseType);
            return extractData(response);
        } catch (RestClientResponseException ex) {
            throw handleError("PUT", uri, ex);
        }
    }

    protected void putVoid(String uri, Object body, Object... uriVars) {
        try {
            restClient.put()
                    .uri(uri, uriVars)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException ex) {
            throw handleError("PUT", uri, ex);
        }
    }

    private <T> T extractData(ApiResponse<T> response) {
        if (response == null || !response.isSuccess()) {
            throw new BusinessException(serviceName + " returned error", HttpStatus.BAD_GATEWAY);
        }
        return response.getData();
    }

    private BusinessException handleError(String method, String uri, RestClientResponseException ex) {
        log.error("[{}] {} {} failed: {} - {}", serviceName, method, uri, ex.getStatusCode(), ex.getResponseBodyAsString());
        HttpStatus status = ex.getStatusCode().is4xxClientError()
                ? HttpStatus.valueOf(ex.getStatusCode().value())
                : HttpStatus.SERVICE_UNAVAILABLE;
        return new BusinessException(serviceName + " unavailable", status);
    }
}
