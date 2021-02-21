package pl.polsl.io.mytoolyourtool.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r where r.offer.lender.id=?1")
    Optional<List<Reservation>> findMyLoans(Long lenderId);

    @Query("SELECT r FROM Reservation r where r.borrower.id=?1")
    Optional<List<Reservation>> findMyReservations(Long borrowerId);

    @Query("SELECT r.offer FROM Reservation r WHERE r.offer.id=?1")
    Optional<Offer>findOfferById(Long offerId);

}
