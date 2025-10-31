package dentsu.bands.external;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import dentsu.bands.external.model.Album;
import dentsu.bands.external.model.Band;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExternalApiCache {

    private final ExternalApiService externalApiService;

    private static final String SINGLETON_KEY = "only";

    private final Cache<String, List<Band>> bandCache = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(10))
        .maximumSize(1)
        .build();

    private final Cache<String, List<Album>> albumCache = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(10))
        .maximumSize(1)
        .build();

    public List<Band> getAllBands() {
        return bandCache.get(SINGLETON_KEY, key -> externalApiService.getAllBands());
    }

    public List<Album> getAllAlbums() {
        return albumCache.get(SINGLETON_KEY, key -> externalApiService.getAllAlbums());
    }

}
