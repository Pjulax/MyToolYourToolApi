package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.ToolQuality;

@Getter
@AllArgsConstructor
public class AddOfferDTO {
    private final Long categoryId;
    private final String toolName;
    private final String description;
    private final ToolQuality toolQuality;
}