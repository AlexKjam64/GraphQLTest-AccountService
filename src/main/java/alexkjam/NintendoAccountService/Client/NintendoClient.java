package alexkjam.NintendoAccountService.Client;

import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class NintendoClient {
    private final WebClient webClient;

    public NintendoClient(WebClient webClient){
        this.webClient = webClient;
    }

    public <T>T retrieve(String urlString, ParameterizedTypeReference<T> type){
        return webClient
        .method(HttpMethod.GET)
        .uri(urlString)
        .exchangeToMono(response->transfrom(response, type))
        .block();
    }

    public <T>T retrieveBatch(String urlString, ParameterizedTypeReference<T> type, List<String> ids){
        return webClient
        .method(HttpMethod.POST)
        .uri(urlString)
        .body(BodyInserters.fromValue(ids))
        .exchangeToMono(response->transfrom(response, type))
        .block();
    }

    @SuppressWarnings("null")
    private <T>Mono<T> transfrom(ClientResponse response, ParameterizedTypeReference<T> type){
        var status = HttpStatus.valueOf(response.statusCode().value());

        if(status.is2xxSuccessful()){
            return response.bodyToMono(type);
        }else if(status.value() == 404){
            return Mono.error(WebClientResponseException.create(404, null, null, null, null));
        }

        return response.bodyToMono(type);
    }
}