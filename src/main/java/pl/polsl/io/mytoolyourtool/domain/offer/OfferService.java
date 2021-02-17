package pl.polsl.io.mytoolyourtool.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.AddOfferDTO;
import pl.polsl.io.mytoolyourtool.domain.category.Category;
import pl.polsl.io.mytoolyourtool.domain.category.CategoryRepository;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectDoesNotExistException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public void addOffer(AddOfferDTO addOfferDTO) {
        if (addOfferDTO.getCategoryId() == null || addOfferDTO.getToolName() == null
                || addOfferDTO.getDescription() == null || addOfferDTO.getToolQuality() == null) {
            throw new IllegalArgumentException("Provided bad data for new offer.");
        } else {
            Category category = categoryRepository.findById(addOfferDTO.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category with id: " + addOfferDTO.getCategoryId() + " doesn't exist."));
            Offer offer = Offer.builder()
                    .toolName(addOfferDTO.getToolName())
                    .description(addOfferDTO.getDescription())
                    .toolQuality(addOfferDTO.getToolQuality())
                    .lender(userService.whoami())
                    .build();
            offer = offerRepository.save(offer);
            category.getOffers().add(offer);
            categoryRepository.save(category);
        }
    }

    public List<Offer> getMyOffers(Long id) {
        return offerRepository.findOffersByLenderId(id);
    }

    public Optional<Offer> getSpecificOffer(Long offerId) {
        if (offerRepository.findById(offerId).isEmpty()) {
            throw new ObjectDoesNotExistException("Offer with id:" + offerId + " does not exist.");
        }
        return offerRepository.findById(offerId);
    }
}