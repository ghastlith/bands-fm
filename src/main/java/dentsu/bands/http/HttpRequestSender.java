package dentsu.bands.http;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dentsu.bands.http.exception.HttpErrorResponseException;
import dentsu.bands.http.exception.InvalidURLException;
import dentsu.bands.http.exception.JsonParsingException;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Utility class for HTTP actions.
 */
@Component
@RequiredArgsConstructor
public class HttpRequestSender {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    /**
     * Sends an HTTP GET Request to an specified url and returns the response body
     * as an string.
     *
     * @param url the desired url to make the request
     * @return The Response Body.
     */
    public <T> T doGetRequest(final String url, final Type type) {
        val uri = buildUri(url);

        val request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();
        val response = getResponse(httpClient, request);

        if (!HttpStatus.valueOf(response.statusCode()).is2xxSuccessful()) {
            throw new HttpErrorResponseException(response.statusCode());
        }

        val responseBody = response.body();

        try {
            val jacksonType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(responseBody, jacksonType);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException();
        }
    }

    private URI buildUri(final String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new InvalidURLException(url);
        }
    }

    private HttpResponse<String> getResponse(final HttpClient httpClient, final HttpRequest request) {
        try {
            return httpClient.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpErrorResponseException(500);
        }
    }

}
