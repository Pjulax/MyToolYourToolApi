package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;
import pl.polsl.io.mytoolyourtool.domain.reservation.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/reservations")
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;

    @GetMapping
    public String hello(){
        return "Hello at mytoolyourtool reservations api";
    }

    @PostMapping(path="/my-loans", produces = "application/json")
    public Optional<List<Reservation>> getMyLendingCart(@RequestBody Long lenderId )
    {
        return reservationService.getMyLendingCart(lenderId);
    }

    @PostMapping(path="/my-reservations", produces="application/json")
    public Optional<List<Reservation>> getMyBorrowingCart(@RequestBody Long borrowerId)
    {
        return reservationService.getMyBorrowingCart(borrowerId);
    }

    @PostMapping(path="/add-reservation")
    public void addReservation(@RequestBody Reservation reservation)
    {
        reservationService.addReservation(reservation);
    }

}
