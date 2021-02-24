package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.ChooseReservationDTO;
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

    public List<Reservation> getMyLendingCart() {
        User user = userService.whoami();
        return reservationRepository.findByOffer_Lender_IdAndFinishedIsFalse(user.getId()).orElse( List.of() );
    }

    public List<Reservation> getMyBorrowingCart() {
        User user = userService.whoami();
        return reservationRepository.findByBorrower_IdAndFinishedIsFalse(user.getId()).orElse( List.of() );
    }

    public void addReservation(Long offerId) {
        if (null == offerId) {
            throw new IllegalArgumentException("Provided bad data for new reservation.");
        }
            else {
                User borrower = userService.whoami();
                Optional<Offer> offer = offerRepository.findById(offerId);
                if(offer.isPresent())
                {
                    Reservation reservation = Reservation.builder()
                            .borrower(borrower)
                            .offer(offer.get())
                            .isChosen(false)
                            .isFinished(false)
                            .build();
                    reservationRepository.save(reservation);
                }
                else
                {
                    throw new EntityNotFoundException("Offer with id: " + offerId + " does not exist.");
                }
            }
    }

    public void chooseReservation(ChooseReservationDTO chooseReservationDTO) {
        Reservation chosenReservation = reservationRepository.findById(chooseReservationDTO.getReservationId()).orElseThrow(() -> new IllegalArgumentException("Reservation with id:" + chooseReservationDTO.getReservationId() + " does not exist."));
        chosenReservation.setChosen(true);
        chosenReservation.getOffer().setReservationChosen(true);
        Offer offer = offerRepository.save(chosenReservation.getOffer());
        List<Reservation> notChosenReservations = reservationRepository.findByOffer_IdAndChosenIsFalse(offer.getId()).orElseThrow(() -> new IllegalArgumentException("Reservations or offer with id: " + offer.getId() + " does not exists."));
        for(int i = 0; i < notChosenReservations.size(); i++) {
            notChosenReservations.get(i).setFinished(true);
        }
        reservationRepository.saveAll(notChosenReservations);
    }
}
