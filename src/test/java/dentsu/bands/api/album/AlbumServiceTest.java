package dentsu.bands.api.album;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dentsu.bands.api.exception.NotFoundException;
import dentsu.bands.external.ExternalApiCache;
import dentsu.bands.external.model.Album;
import lombok.val;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    private static final OffsetDateTime BASELINE = OffsetDateTime.now().minusYears(40);
    private static final Album STRAWBERRY = album("Strawberry", BASELINE.plusDays(500));
    private static final Album CHERRY = album("Cherry", BASELINE.plusDays(250));
    private static final Album APPLE = album("Apple", BASELINE.plusDays(800));
    private static final Album MELON = album("Melon", BASELINE.plusDays(400));
    private static final Album ORANGE = album("Orange", BASELINE.plusDays(1200));
    private static final Album QUINOA = album("Quinoa", BASELINE.plusDays(50));
    private static final List<Album> TEST_DATA = List.of(
        STRAWBERRY,
        CHERRY,
        APPLE,
        MELON,
        ORANGE,
        QUINOA
    );

    @Mock
    private ExternalApiCache cache;

    @InjectMocks
    private AlbumService service;

    @Test
    void testGetAll() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.UNORDERED, null);

        // THEN
        assertThat(albums).containsExactly(
            STRAWBERRY,
            CHERRY,
            APPLE,
            MELON,
            ORANGE,
            QUINOA
        );
    }

    @Test
    void testGetAll_orderedByNameAscending() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.NAME_ASCENDING, null);

        // THEN
        assertThat(albums).containsExactly(
            APPLE,
            CHERRY,
            MELON,
            ORANGE,
            QUINOA,
            STRAWBERRY
        );
    }

    @Test
    void testGetAll_orderedByNameDescending() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.NAME_DESCENDING, null);

        // THEN
        assertThat(albums).containsExactly(
            STRAWBERRY,
            QUINOA,
            ORANGE,
            MELON,
            CHERRY,
            APPLE
        );
    }

    @Test
    void testGetAll_orderedByReleasedDateAscending() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.RELEASE_DATE_ASCENDING, null);

        // THEN
        assertThat(albums).containsExactly(
            QUINOA,
            CHERRY,
            MELON,
            STRAWBERRY,
            APPLE,
            ORANGE
        );
    }

    @Test
    void testGetAll_orderedByReleasedDateDescending() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.RELEASE_DATE_DESCENDING, null);

        // THEN
        assertThat(albums).containsExactly(
            ORANGE,
            APPLE,
            STRAWBERRY,
            MELON,
            CHERRY,
            QUINOA
        );
    }

    @Test
    void testGetAll_filtering() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.UNORDERED, "y");

        // THEN
        assertThat(albums).containsExactly(
            STRAWBERRY,
            CHERRY
        );
    }

    @Test
    void testGetAll_filteringRespectsOrder() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(TEST_DATA);

        // WHEN
        val albums = service.getAll(AlbumOrder.RELEASE_DATE_ASCENDING, "y");

        // THEN
        assertThat(albums).containsExactly(
            CHERRY,
            STRAWBERRY
        );
    }

    @Test
    void testGetById() {
        // GIVEN
        val id = UUID.randomUUID();
        val expectedAlbum = album(id);
        when(cache.getAllAlbums()).thenReturn(List.of(
            album(),
            album(),
            expectedAlbum,
            album(),
            album()
        ));

        // WHEN
        val album = service.getById(id);

        // THEN
        assertThat(album).isEqualTo(expectedAlbum);
    }

    @Test
    void testGetById_notFound() {
        // GIVEN
        when(cache.getAllAlbums()).thenReturn(List.of(
            album(),
            album(),
            album(),
            album()
        ));

        // WHEN
        val throwable = catchThrowable(() -> service.getById(UUID.randomUUID()));

        // THEN
        assertThat(throwable).isInstanceOf(NotFoundException.class);
    }

    private static Album album() {
        return mockAlbum()
            .build();
    }

    private static Album album(UUID id) {
        return mockAlbum()
            .id(id)
            .build();
    }

    private static Album album(String name, OffsetDateTime releasedDate) {
        return mockAlbum()
            .name(name)
            .releasedDate(releasedDate)
            .build();
    }

    private static Album.AlbumBuilder mockAlbum() {
        return Album.builder()
            .id(UUID.randomUUID())
            .name("name")
            .image("image")
            .tracks(List.of())
            .releasedDate(OffsetDateTime.now());
    }

}
