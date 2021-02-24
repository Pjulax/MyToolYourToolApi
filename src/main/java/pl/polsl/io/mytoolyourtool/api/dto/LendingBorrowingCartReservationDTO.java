package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class LendingBorrowingCartReservationDTO {
    private final Long id;
    private final Date startDate;
    private final Date endDate;

    /*
    flagi oddania, oceny (tylko jednostronnej? trzeba rozdzielić na dwa obiekty?),
     */

    private final boolean isChosen;
}
