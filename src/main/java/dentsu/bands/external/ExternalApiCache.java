package dentsu.bands.external;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import dentsu.bands.external.model.Album;
import dentsu.bands.external.model.Band;

@Component
public class ExternalApiCache {

    private static final String SINGLETON_KEY = "only";

    private final ExternalApiService externalApiService;
    private final Cache<String, List<Album>> albumCache;
    private final Cache<String, List<Band>> bandCache;

    @Autowired
    public ExternalApiCache(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
        albumCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .maximumSize(1)
            .build();
        bandCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .maximumSize(1)
            .build();
    }

    ExternalApiCache(
        ExternalApiService externalApiService,
        Cache<String, List<Album>> albumCache,
        Cache<String, List<Band>> bandCache
    ) {
        this.externalApiService = externalApiService;
        this.albumCache = albumCache;
        this.bandCache = bandCache;
    }

    public List<Band> getAllBands() {
        return bandCache.get(SINGLETON_KEY, key -> externalApiService.getAllBands());
    }

    public List<Album> getAllAlbums() {
        return albumCache.get(SINGLETON_KEY, key -> externalApiService.getAllAlbums());
    }

}
