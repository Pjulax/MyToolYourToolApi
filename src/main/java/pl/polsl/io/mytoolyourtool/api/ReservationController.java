package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.AddReservationDTO;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;
import pl.polsl.io.mytoolyourtool.domain.reservation.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String hello(){
        return "Hello at mytoolyourtool reservations api";
    }

    @GetMapping(path="/my-loans", produces = "application/json")
    public Optional<List<Reservation>> getMyLendingCart()
    {
        return reservationService.getMyLendingCart();
    }

    @GetMapping(path="/my-reservations", produces="application/json")
    public Optional<List<Reservation>> getMyBorrowingCart()
    {
        return reservationService.getMyBorrowingCart();
    }

    @PostMapping(path="/add-reservation")
    public void addReservation(@RequestBody AddReservationDTO addReservationDTO)
    {
        reservationService.addReservation(addReservationDTO);
    }

}
