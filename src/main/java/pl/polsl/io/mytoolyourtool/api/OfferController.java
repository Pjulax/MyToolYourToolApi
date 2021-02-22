package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.AddOfferDTO;
import pl.polsl.io.mytoolyourtool.api.dto.OfferDTO;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferService;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/offers")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @PostMapping(path = "/add-offer")
    public void addOffer(@RequestBody AddOfferDTO addOfferDTO) {
        offerService.addOffer(addOfferDTO);
    }

    @GetMapping(path = "/myoffers", produces = "application/json")
    public List<OfferDTO> getMyOffers() {
        return offerService.getMyOffers();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public OfferDTO getSpecificOffer(@PathVariable("id") Long offerId) {
        return offerService.getSpecificOffer(offerId);
    }
}
