package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.AddOfferDTO;
import pl.polsl.io.mytoolyourtool.api.dto.OfferDTO;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferService;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/offers")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @DeleteMapping(path = "/delete-offer/{id}")
    public void deleteOffer(@PathVariable("id")Long offerId){
        offerService.deleteOffer(offerId);}

    @PostMapping(path = "/add-offer")
    public void addOffer(@RequestBody AddOfferDTO addOfferDTO) {
        offerService.addOffer(addOfferDTO);
    }

    @PostMapping(path = "/myoffers", produces = "application/json")
    public List<Offer> getMyOffers() {
        User user = userService.whoami();
        return offerService.getMyOffers(user.getId());
    }

    @PostMapping(path = "/{id}", produces = "application/json")
    public Optional<Offer> getSpecificOffer(@PathVariable("id") Long offerId) {
        return offerService.getSpecificOffer(offerId);
    }
}
