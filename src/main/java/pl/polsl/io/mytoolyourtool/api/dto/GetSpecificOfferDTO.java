package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.offer.ToolQuality;

@Getter
@AllArgsConstructor
public class GetSpecificOfferDTO {
    private final Long id;
    private final String toolName;
    private final String description;
    private final ToolQuality toolQuality;
    private final Double lendersAverageRating;
    private final String lendersName;

}
