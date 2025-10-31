package dentsu.bands.external;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import dentsu.bands.external.model.Band;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExternalApiCache {

    private final ExternalApiService externalApiService;

    private static final String SINGLETON_KEY = "only";

    private final Cache<String, List<Band>> cache = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(10))
        .maximumSize(1)
        .build();

    public List<Band> getAllBands() {
        return cache.get(SINGLETON_KEY, key -> externalApiService.getAllBands());
    }

}
