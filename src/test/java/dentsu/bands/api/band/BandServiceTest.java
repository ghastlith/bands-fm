package dentsu.bands.api.band;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dentsu.bands.api.exception.NotFoundException;
import dentsu.bands.external.ExternalApiCache;
import dentsu.bands.external.model.Band;
import lombok.val;

@ExtendWith(MockitoExtension.class)
class BandServiceTest {

    private static final Band SPOON = band("Spoon", 500);
    private static final Band COLDPLAY = band("Coldplay", 250);
    private static final Band ACDC = band("AC/DC", 800);
    private static final Band MUSE = band("Muse", 400);
    private static final Band OASIS = band("Oasis", 1200);
    private static final Band QUEEN = band("Queen", 50);
    private static final List<Band> TEST_DATA = List.of(
        SPOON,
        COLDPLAY,
        ACDC,
        MUSE,
        OASIS,
        QUEEN
    );

    @Mock
    private ExternalApiCache cache;

    @InjectMocks
    private BandService service;

    @Test
    void testGetAll() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.UNORDERED, null);

        // THEN
        assertThat(bands).containsExactly(
            SPOON,
            COLDPLAY,
            ACDC,
            MUSE,
            OASIS,
            QUEEN
        );
    }

    @Test
    void testGetAll_orderedByNameAscending() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.NAME_ASCENDING, null);

        // THEN
        assertThat(bands).containsExactly(
            ACDC,
            COLDPLAY,
            MUSE,
            OASIS,
            QUEEN,
            SPOON
        );
    }

    @Test
    void testGetAll_orderedByNameDescending() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.NAME_DESCENDING, null);

        // THEN
        assertThat(bands).containsExactly(
            SPOON,
            QUEEN,
            OASIS,
            MUSE,
            COLDPLAY,
            ACDC
        );
    }

    @Test
    void testGetAll_orderedByPopularityAscending() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.POPULARITY_ASCENDING, null);

        // THEN
        assertThat(bands).containsExactly(
            QUEEN,
            COLDPLAY,
            MUSE,
            SPOON,
            ACDC,
            OASIS
        );
    }

    @Test
    void testGetAll_orderedByPopularityDescending() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.POPULARITY_DESCENDING, null);

        // THEN
        assertThat(bands).containsExactly(
            OASIS,
            ACDC,
            SPOON,
            MUSE,
            COLDPLAY,
            QUEEN
        );
    }

    @Test
    void testGetAll_filtering() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.UNORDERED, "u");

        // THEN
        assertThat(bands).containsExactly(
            MUSE,
            QUEEN
        );
    }

    @Test
    void testGetAll_filteringRespectsOrder() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(TEST_DATA);

        // WHEN
        val bands = service.getAll(BandOrder.NAME_DESCENDING, "u");

        // THEN
        assertThat(bands).containsExactly(
            QUEEN,
            MUSE
        );
    }

    @Test
    void testGetById() {
        // GIVEN
        val id = UUID.randomUUID();
        val expectedBand = band(id);
        when(cache.getAllBands()).thenReturn(List.of(
            band(),
            band(),
            expectedBand,
            band(),
            band()
        ));

        // WHEN
        val band = service.getById(id);

        // THEN
        assertThat(band).isEqualTo(expectedBand);
    }

    @Test
    void testGetById_notFound() {
        // GIVEN
        when(cache.getAllBands()).thenReturn(List.of(
            band(),
            band(),
            band(),
            band()
        ));

        // WHEN
        val throwable = catchThrowable(() -> service.getById(UUID.randomUUID()));

        // THEN
        assertThat(throwable).isInstanceOf(NotFoundException.class);
    }

    private static Band band() {
        return mockBand()
            .build();
    }

    private static Band band(UUID id) {
        return mockBand()
            .id(id)
            .build();
    }

    private static Band band(String name, int numPlays) {
        return mockBand()
            .name(name)
            .numPlays(numPlays)
            .build();
    }

    private static Band.BandBuilder mockBand() {
        return Band.builder()
            .id(UUID.randomUUID())
            .name("name")
            .numPlays(10)
            .image("image")
            .genre("genre")
            .biography("bio")
            .albums(List.of());
    }

}
