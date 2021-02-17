package pl.polsl.io.mytoolyourtool.domain.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectDoesNotExistException;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectExistsException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    @Autowired
    private final OfferRepository offerRepository;

    public void addOffer(Offer offer) {
        if(offer.getDescription()==null || offer.getLender()==null||offer.getToolName()==null||offer.getId()==null)
        {
            //WYJON BO NULE
            return;
        }
        else
        {
            offerRepository.save(offer);
        }
    }

    public Optional<List<Offer>> getMyOffers(Long id) {
        return offerRepository.findOffersByLenderId(id);
    }

    public Optional<Offer> getSpecificOffer(Long offerId) {
        if(offerRepository.findById(offerId).isEmpty())
        {
            throw new ObjectDoesNotExistException("Offer with id:"+offerId+" does not exist.");
        }
        return offerRepository.findById(offerId);
    }
}
