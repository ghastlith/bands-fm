package dentsu.bands.external.model;

import java.util.List;
import java.util.UUID;

public record Album (
    UUID id,
    String name,
    String releasedDate,
    String image,
    Band band,
    List<Track> tracks
) {

}
