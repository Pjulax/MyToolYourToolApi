package pl.polsl.io.mytoolyourtool.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
