package com.edu.rent.client;

import com.edu.rent.api.model.request.ToolQuantityUpdateRequest;
import com.edu.rent.api.model.response.ListToolResponse;
import com.edu.rent.api.model.response.ToolResponse;
import com.edu.rent.exception.ApiErrorResponse;
import com.edu.rent.exception.BadRequestException;
import com.edu.rent.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class ToolClient {
    private final WebClient webClient;

    public ToolClient(
        WebClient.Builder webClientBuilder,
        @Value("${app.tool-url}")
        String baseUrl
    ) {
        log.info(baseUrl);
        this.webClient = webClientBuilder.filter(errorHandlingFilter()).baseUrl(baseUrl).build();
    }
    public void updateQuantity(List<ToolQuantityUpdateRequest> tools, String operation) {
        webClient.method(HttpMethod.PATCH)
            .uri(uriBuilder -> uriBuilder
                .path("update-quantities")
                .queryParam("param", operation)
                .build())
            .body(Mono.just(tools), ToolQuantityUpdateRequest.class)
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    public ListToolResponse fetchTools(List<UUID> toolIds) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("by-ids")
                    .queryParam("listIds", toolIds.toArray())
                    .build())
                .retrieve()
                .bodyToMono(ListToolResponse.class)
                .block();
    }

    private ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode() == null) {
                return Mono.just(clientResponse);
            }
            if (clientResponse.statusCode().is5xxServerError()) {
                return handle5xxError();
            } else if (clientResponse.statusCode().is4xxClientError()) {
                return handle4xxError(clientResponse);
            }
            return Mono.just(clientResponse);
        });
    }
    private Mono<ClientResponse> handle4xxError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ApiErrorResponse.class)
            .flatMap(errorBody -> {
                String method = clientResponse.request().getMethod().toString();
                String path = clientResponse.request().getURI().getPath();
                log.error(String.format(
                    "При выполнении операции {%s} {%s} возникла ошибка: {%s}",
                    method,
                    path,
                    errorBody.message()
                ));
                return Mono.error(new BadRequestException(errorBody.message()));
            });
    }

    private Mono<ClientResponse> handle5xxError() {
        return Mono.error(new InternalServerErrorException("Ошибка сервиса tool"));
    }
}
