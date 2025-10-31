package dentsu.bands.external.model;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record Band(
    String name,
    String image,
    String genre,
    String biography,
    Integer numPlays,
    UUID id,
    List<String> albums
) {

}
