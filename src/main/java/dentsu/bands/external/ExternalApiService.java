package dentsu.bands.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import dentsu.bands.external.model.Band;
import dentsu.bands.http.HttpRequestSender;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final HttpRequestSender httpRequestSender;

    @Value("${external.bands.api.base-url}")
    private String externalApiBaseUrl;

    private static final String BANDS_ENDPOINT = "/bands";

    public List<Band> getAllBands() {
        val url = externalApiBaseUrl + BANDS_ENDPOINT;
        List<Band> response = httpRequestSender.doGetRequest(url, new TypeReference<List<Band>>() {}.getType());

        return response;
    }

}
