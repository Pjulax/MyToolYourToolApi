package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;

@Getter
@AllArgsConstructor
public class LendingCartReservationDTO {
    private final Long id;
    private final Long offerId;
    private final String borrowerEmail;
    private final String borrowerFullName;
    private final boolean chosen;
    private final boolean returned;
    private final boolean reviewedBorrower;

    public static LendingCartReservationDTO fromDomain(Reservation reservation){
        return new LendingCartReservationDTO(reservation.getId(), reservation.getOffer().getId(),
                reservation.getBorrower().getEmail(),
                reservation.getBorrower().getFirstName() + " " + reservation.getBorrower().getLastName(),
                reservation.isChosen(), reservation.getOffer().isReturned(), reservation.getOffer().isBorrowerReviewed());
    }
}
