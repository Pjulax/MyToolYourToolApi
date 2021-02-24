package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.BorrowingCartReservationDTO;
import pl.polsl.io.mytoolyourtool.api.dto.LendingCartReservationDTO;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferRepository;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OfferRepository offerRepository;
    private final UserService userService;

    public List<LendingCartReservationDTO> getMyLendingCart() {
        User user = userService.whoami();
        List<Reservation> reservations = reservationRepository.findByOffer_Lender_IdAndFinishedIsFalse(user.getId()).orElse( List.of() );
        return reservations.stream().map(LendingCartReservationDTO::fromDomain).collect(Collectors.toList());
    }

    public List<BorrowingCartReservationDTO> getMyBorrowingCart() {
        User user = userService.whoami();
        List<Reservation> reservations = reservationRepository.findByBorrower_IdAndFinishedIsFalse(user.getId()).orElse( List.of() );
        return reservations.stream().map(BorrowingCartReservationDTO::fromDomain).collect(Collectors.toList());
    }

    public void addReservation(Long offerId) {
        if (null == offerId) {
            throw new IllegalArgumentException("Provided bad data for new reservation.");
        }
            else {
                User borrower = userService.whoami();
                Optional<Offer> offer = offerRepository.findById(offerId);
                if(offer.isPresent()) {
                    if( !offer.get().getLender().equals(borrower)) {
                        if(reservationRepository.findByOffer_IdAndBorrower_Id(offerId, borrower.getId()).isEmpty()) {
                            Reservation reservation = Reservation.builder()
                                    .borrower(borrower)
                                    .offer(offer.get())
                                    .chosen(false)
                                    .finished(false)
                                    .build();
                            reservationRepository.save(reservation);
                        } else {
                            throw new IllegalArgumentException("User cannot place more than 1 reservation on offer.");
                        }
                    } else {
                        throw new IllegalArgumentException("User cannot place reservation to his offers.");
                    }
                } else {
                    throw new EntityNotFoundException("Offer with id: " + offerId + " does not exist.");
                }
            }
    }

    public void chooseReservation(Long reservationId) {
        User user = userService.whoami();
        Reservation chosenReservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation with id:" + reservationId + " does not exist."));
        if( user.equals(chosenReservation.getOffer().getLender())) {
            chosenReservation.setChosen(true);
            chosenReservation.getOffer().setReservationChosen(true);
            Offer offer = offerRepository.save(chosenReservation.getOffer());
            List<Reservation> notChosenReservations = reservationRepository.findByOffer_IdAndChosenIsFalse(offer.getId()).orElse(List.of());
            if (!notChosenReservations.isEmpty()) {
                for (int i = 0; i < notChosenReservations.size(); i++) {
                    notChosenReservations.get(i).setFinished(true);
                }
                reservationRepository.saveAll(notChosenReservations);
            }
        } else {
            throw new IllegalArgumentException("User not owning offer cannot choose reservation.");
        }
    }

    public void checkReturnedReservation(Long reservationId) {
        User user = userService.whoami();
        Reservation returnedReservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation with id:" + reservationId + " does not exist."));
        if( user.equals(returnedReservation.getOffer().getLender())) {
            if(!returnedReservation.isChosen())
                throw new IllegalArgumentException("User cannot return while tool has not chosen reservation.");
            returnedReservation.getOffer().setReturned(true);
            offerRepository.save(returnedReservation.getOffer());
        } else {
            throw new IllegalArgumentException("User not owning offer cannot set reservation returned.");
        }
    }
}
