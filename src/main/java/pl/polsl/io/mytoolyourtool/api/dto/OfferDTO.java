package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.ToolQuality;

@Getter
@AllArgsConstructor
public class OfferDTO {
    private final Long id;
    private final String toolName;
    private final String description;
    private final ToolQuality toolQuality;

    public static OfferDTO fromDomain(Offer offer) {
        return new OfferDTO(offer.getId(), offer.getToolName(), offer.getDescription(), offer.getToolQuality());
    }
}