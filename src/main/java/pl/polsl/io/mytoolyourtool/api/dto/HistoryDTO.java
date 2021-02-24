package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;

@Getter
@AllArgsConstructor
public class HistoryDTO {
    private final String offerName;

    public static HistoryDTO fromDomain(Offer offer){
        return new HistoryDTO(offer.getToolName());
    }
}
