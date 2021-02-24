package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.AddOfferDTO;
import pl.polsl.io.mytoolyourtool.api.dto.GetSpecificOfferDTO;
import pl.polsl.io.mytoolyourtool.api.dto.OfferDTO;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferService;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping(path = "/add-offer")
    public void addOffer(@RequestBody AddOfferDTO addOfferDTO) {
        offerService.addOffer(addOfferDTO);
    }

    @GetMapping(path = "/myoffers")
    public List<OfferDTO> getMyOffers() {
        return offerService.getMyOffers();
    }

    @GetMapping(path = "/{id}")
    public GetSpecificOfferDTO getSpecificOffer(@PathVariable("id") Long offerId) {
        return offerService.getSpecificOffer(offerId);
    }
    @GetMapping(path="/tool-quality")
    public List<String> getToolQualities()
    {
        return offerService.getToolQualities();
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteOffer(@PathVariable("id")Long offerId){offerService.deleteOffer(offerId);}
}
