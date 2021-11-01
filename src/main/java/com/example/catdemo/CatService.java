package com.example.catdemo;

import com.example.catdemo.models.OutgoingCatPictureDTO;
import com.example.catdemo.models.SearchResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Clock;

@Service
public class CatService {

    private final RestTemplate restTemplate;
    private final Clock clock;
    private final WebClient webClient;

    @Autowired
    public CatService(Clock clock, WebClient webClient) {
        this.clock = clock;
        this.webClient = webClient;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public OutgoingCatPictureDTO getCatPicture() {
        var searchResponseDTO =  webClient.get().uri("v1/images/search")
                .retrieve()
                .bodyToMono(SearchResponseDTO.class)
                .block();

//        restTemplate.getForObject("https://api.thecatapi.com", SearchResponseDTO.class);

        return new OutgoingCatPictureDTO(
                searchResponseDTO.getUrl(),
                this.clock.instant().toString()
        );
    }
}
