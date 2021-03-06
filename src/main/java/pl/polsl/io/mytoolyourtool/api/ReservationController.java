package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.BorrowingCartReservationDTO;
import pl.polsl.io.mytoolyourtool.api.dto.LendingCartReservationDTO;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;
import pl.polsl.io.mytoolyourtool.domain.reservation.ReservationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String hello(){
        return "Hello at mytoolyourtool reservations api";
    }

    @GetMapping(path="/my-loans")
    public List<LendingCartReservationDTO> getMyLendingCart()
    {
        return reservationService.getMyLendingCart();
    }

    @GetMapping(path="/my-reservations")
    public List<BorrowingCartReservationDTO> getMyBorrowingCart()
    {
        return reservationService.getMyBorrowingCart();
    }

    @PostMapping(path="/add-reservation/{offer-id}")
    public void addReservation(@PathVariable("offer-id") Long offerId)
    {
        reservationService.addReservation(offerId);
    }

    @PostMapping(path="/choose-reservation/{id}")
    public void chooseReservation(@PathVariable("id") Long reservationId) {
        reservationService.chooseReservation(reservationId);
    }

    @PostMapping(path="/check-returned-reservation/{id}")
    public void checkReturnedReservation(@PathVariable("id") Long reservationId) {
        reservationService.checkReturnedReservation(reservationId);
    }
}