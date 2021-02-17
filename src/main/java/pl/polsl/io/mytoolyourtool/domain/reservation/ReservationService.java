package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;

    public Optional<List<Reservation>> getMyLendingCart() {
        User user = userService.whoami();
        return reservationRepository.findMyLoans(user.getId());
    }

    public Optional<List<Reservation>> getMyBorrowingCart() {
        User user = userService.whoami();
        return reservationRepository.findMyReservations(user.getId());
    }

    public void addReservation(Reservation reservation) {
        if(reservation.getBorrower()==null||reservation.getEndDate()==null||reservation.getStartDate()==null||reservation.getOffer()==null)
        {
            return;
        }
        reservationRepository.save(reservation);
    }
}
