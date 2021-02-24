package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;

@Getter
@AllArgsConstructor
public class BorrowingCartReservationDTO {
    private final Long id;
    private final Long offerId;
    private final String lenderEmail;
    private final String lenderFullName;
    private final boolean chosen;
    private final boolean returned;
    private final boolean reviewedLender;

    public static BorrowingCartReservationDTO fromDomain(Reservation reservation){
        return new BorrowingCartReservationDTO(reservation.getId(), reservation.getOffer().getId(),
                reservation.getOffer().getLender().getEmail(),
                reservation.getOffer().getLender().getFirstName() + " " + reservation.getOffer().getLender().getLastName(),
                reservation.isChosen(), reservation.getOffer().isReturned(), reservation.getOffer().isLenderReviewed());
    }
}
