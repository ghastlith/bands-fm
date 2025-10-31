package dentsu.bands.external;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.testing.FakeTicker;

@ExtendWith(MockitoExtension.class)
class ExternalApiCacheTest {

    @Mock
    private ExternalApiService externalApiService;
    private ExternalApiCache externalApiCache;

    @Test
    void shouldCacheResults() {
        // GIVEN
        externalApiCache = new ExternalApiCache(externalApiService);

        // first call caches
        externalApiCache.getAllBands();
        externalApiCache.getAllAlbums();

        // WHEN
        // second call fetches from cache
        externalApiCache.getAllBands();
        externalApiCache.getAllAlbums();

        // THEN
        // external API is hit only once
        verify(externalApiService, atMostOnce()).getAllAlbums();
        verify(externalApiService, atMostOnce()).getAllBands();
    }

    @Test
    void testCacheRefresh() {
        // GIVEN
        final var ticker = new FakeTicker();
        externalApiCache = new ExternalApiCache(
            externalApiService,
            Caffeine.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(Duration.ofMinutes(10))
                .ticker(ticker::read)
                .build(),
            Caffeine.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(Duration.ofMinutes(10))
                .ticker(ticker::read)
                .build()
        );

        // first call caches
        externalApiCache.getAllBands();
        externalApiCache.getAllAlbums();

        // but we fast-forward and the cache expires
        ticker.advance(Duration.ofMinutes(30));


        // WHEN
        // second call has to refresh the cache
        externalApiCache.getAllBands();
        externalApiCache.getAllAlbums();

        // THEN
        // external API is hit twice
        verify(externalApiService, times(2)).getAllAlbums();
        verify(externalApiService, times(2)).getAllBands();
    }

}
