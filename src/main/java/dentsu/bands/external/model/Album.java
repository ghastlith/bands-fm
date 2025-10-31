package dentsu.bands.external.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record Album (
    UUID id,
    String name,
    OffsetDateTime releasedDate,
    String image,
    Band band,
    List<Track> tracks
) {

}
