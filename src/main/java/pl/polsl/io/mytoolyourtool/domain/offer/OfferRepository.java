package pl.polsl.io.mytoolyourtool.domain.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Query("SELECT o FROM Offer o WHERE o.lender.id=?1")
    List<Offer> findOffersByLenderId(Long lenderID);

    List<Offer> findByLender_IdAndReservationChosenIsFalse(Long lenderId);

    List<Offer> findByLender_IdAndReservationChosenIsFalseAndReturnedIsTrue(Long lenderId);
}
