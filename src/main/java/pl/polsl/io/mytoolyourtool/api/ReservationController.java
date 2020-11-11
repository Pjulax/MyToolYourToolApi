package pl.polsl.io.mytoolyourtool.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/reservations")
public class ReservationController {

    @GetMapping
    public String hello(){
        return "Hello at mytoolyourtool reservations api";
    }
}
