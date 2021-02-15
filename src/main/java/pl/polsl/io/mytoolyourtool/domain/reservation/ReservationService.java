package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Optional<List<Reservation>> getMyLendingCart(Long lenderId) {
        return reservationRepository.findMyLoans(lenderId);
    }

    public Optional<List<Reservation>> getMyBorrowingCart(Long borrowerId) {
        return reservationRepository.findMyReservations(borrowerId);
    }

    public void addReservation(Reservation reservation) {
        if(reservation.getBorrower()==null||reservation.getEndDate()==null||reservation.getStartDate()==null||reservation.getOffer()==null)
        {
            return;
        }
        reservationRepository.save(reservation);
    }
}
