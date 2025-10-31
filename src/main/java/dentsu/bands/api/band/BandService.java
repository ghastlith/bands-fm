package dentsu.bands.api.band;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dentsu.bands.api.exception.NotFoundException;
import dentsu.bands.external.ExternalApiCache;
import dentsu.bands.external.model.Band;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class BandService {

    private final ExternalApiCache externalApiCache;

    public List<Band> getAll(final String orderParam, final String filter) {
        val order = BandOrder.valueOf(orderParam);

        return externalApiCache.getAllBands()
            .stream()
            .filter(band -> null == filter || band.name().contains(filter))
            .sorted((b1, b2) -> {
                return switch (order) {
                    case NAME_ASCENDING -> b1.name().compareToIgnoreCase(b2.name());
                    case NAME_DESCENDING -> b2.name().compareToIgnoreCase(b1.name());
                    case POPULARITY_ASCENDING -> Integer.compare(b1.numPlays(), b2.numPlays());
                    case POPULARITY_DESCENDING -> Integer.compare(b2.numPlays(), b1.numPlays());
                    default -> 0;
                };
            }).toList();
    }

    public Band getById(final UUID id) {
        return externalApiCache.getAllBands()
            .stream()
            .filter(band -> band.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(id.toString()));
    }

}
