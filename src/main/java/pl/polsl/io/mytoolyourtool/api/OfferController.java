package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/offers")
public class OfferController {

    @Autowired
    private final OfferService offerService;


    @PostMapping(path = "/add-offer")
    public void addOffer(@RequestBody Offer offer)
    {
        offerService.addOffer(offer);
    }
    @PostMapping(path = "/myoffers", produces = "application/json")
    public Optional<List<Offer>> getMyOffers(@RequestBody Long lenderId)
    {
       return offerService.getMyOffers(lenderId);
    }

    @PostMapping(path="{offerId}", produces = "application/json")
    public Optional<Offer> getSpecificOffer(@RequestBody Long offerId){return offerService.getSpecificOffer(offerId);}

}
