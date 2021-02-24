package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.AddReservationDTO;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferRepository;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OfferRepository offerRepository;
    private final UserService userService;

    public Optional<List<Reservation>> getMyLendingCart() {
        User user = userService.whoami();
        return reservationRepository.findMyLoans(user.getId());
    }

    public Optional<List<Reservation>> getMyBorrowingCart() {
        User user = userService.whoami();
        return reservationRepository.findMyReservations(user.getId());
    }

    public void addReservation(AddReservationDTO addReservationDTO) {
        if (addReservationDTO.getEndDate() == null ||addReservationDTO.getStartDate() == null
                || addReservationDTO.getOfferId() == null) {
            throw new IllegalArgumentException("Provided bad data for new reservation.");
        }
            else {
                User borrower = userService.whoami();
                Optional<Offer> offer = offerRepository.findById(addReservationDTO.getOfferId());
                if(offer.isPresent())
                {
                    Reservation reservation = Reservation.builder()
                            .borrower(borrower)
                            .startDate(addReservationDTO.getStartDate())
                            .endDate(addReservationDTO.getEndDate())
                            .offer(offer.get())
                            .isChosen(false)
                            .isFinished(false)
                            .build();
                    reservationRepository.save(reservation);
                }
                else
                {
                    throw new EntityNotFoundException("Offer with id: "+ addReservationDTO.getOfferId()+" does not exist.");
                }
            }
    }
}
