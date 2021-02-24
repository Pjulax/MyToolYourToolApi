package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.ChooseReservationDTO;
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
    public List<Reservation> getMyLendingCart()
    {
        return reservationService.getMyLendingCart();
    }

    @GetMapping(path="/my-reservations")
    public List<Reservation> getMyBorrowingCart()
    {
        return reservationService.getMyBorrowingCart();
    }

    @PostMapping(path="/add-reservation/{id}")
    public void addReservation(@PathVariable("id") Long offerId)
    {
        reservationService.addReservation(offerId);
    }

    @PostMapping(path="/choose-reservation")
    public void chooseReservation(@RequestBody ChooseReservationDTO chooseReservationDTO) {
        reservationService.chooseReservation(chooseReservationDTO);
    }
}