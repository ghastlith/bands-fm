package dentsu.bands.api.album;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dentsu.bands.api.exception.NotFoundException;
import dentsu.bands.external.ExternalApiCache;
import dentsu.bands.external.model.Album;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final ExternalApiCache externalApiCache;

    public List<Album> getAll(final AlbumOrder order, final String filter) {
        return externalApiCache.getAllAlbums()
            .stream()
            .filter(album -> null == filter || album.name().contains(filter))
            .sorted((a1, a2) -> {
                return switch (order) {
                    case UNORDERED -> 0;
                    case NAME_ASCENDING -> a1.name().compareToIgnoreCase(a2.name());
                    case NAME_DESCENDING -> a2.name().compareToIgnoreCase(a1.name());
                    case RELEASE_DATE_ASCENDING -> a1.releasedDate().compareTo(a2.releasedDate());
                    case RELEASE_DATE_DESCENDING -> a2.releasedDate().compareTo(a1.releasedDate());
                };
            }).toList();
    }

    public Album getById(final UUID id) {
        return externalApiCache.getAllAlbums()
            .stream()
            .filter(album -> album.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(id.toString()));
    }

}
