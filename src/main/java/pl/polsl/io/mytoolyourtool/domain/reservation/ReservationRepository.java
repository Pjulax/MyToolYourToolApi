package pl.polsl.io.mytoolyourtool.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r where r.offer.lender.id=?1")
    Optional<List<Reservation>> findMyLoans(Long lenderId);

    @Query("SELECT r FROM Reservation r where r.borrower.id=?1")
    Optional<List<Reservation>> findMyReservations(Long borrowerId);

    Optional<List<Reservation>> findByOffer_Lender_IdAndFinishedIsFalse(Long lenderId);

    Optional<List<Reservation>> findByBorrower_IdAndFinishedIsFalse(Long borrowerId);

    Optional<List<Reservation>> findByOffer_IdAndChosenIsFalse(Long offerId);

    Optional<Reservation> findByOffer_IdAndBorrower_Id(Long id, Long borrowerId);
}
