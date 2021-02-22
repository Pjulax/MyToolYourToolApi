package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class AddReservationDTO {
    private final Date startDate;
    private final Date endDate;
    private final Long offerId;
}
