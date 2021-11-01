package com.example.catdemo;

import com.example.catdemo.models.OutgoingCatPictureDTO;
import com.example.catdemo.models.SearchResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CatServiceTest {

    CatService catService;
    @MockBean
    Clock clock;

    ObjectMapper objectMapper = new ObjectMapper();

    public MockWebServer mockCatServer;

    @BeforeEach
    void setUp() throws IOException {
        Mockito.when(clock.instant()).thenReturn(Instant.MIN);

        this.mockCatServer = new MockWebServer();
        this.mockCatServer.start();

        String baseUrl = String.format("http://localhost:%s",
                mockCatServer.getPort());

        WebClient webClient = WebClient.create(baseUrl);

        catService = new CatService(clock, webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.mockCatServer.shutdown();
    }

    @Test
    void returnsFixedTime_whenClockIsMocked() throws JsonProcessingException {
        var fakeSearchResponseDTO = new SearchResponseDTO();
        fakeSearchResponseDTO.setId("id");
        fakeSearchResponseDTO.setUrl("www.url.com");
        mockCatServer.enqueue(
                new MockResponse()
                        .setBody(objectMapper.writeValueAsString(fakeSearchResponseDTO))
                        .addHeader("Content-Type", "application/json")
        );

        var actual = catService.getCatPicture();
        var expected = new OutgoingCatPictureDTO("www.url.com", "-1000000000-01-01T00:00:00Z");


        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
