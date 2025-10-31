package dentsu.bands.external.model;

import java.util.UUID;

import lombok.Builder;

@Builder
public record Track(
    UUID id,
    String name,
    String duration
) {

}
