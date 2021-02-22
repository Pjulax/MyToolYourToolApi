package pl.polsl.io.mytoolyourtool.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.AddOfferDTO;
import pl.polsl.io.mytoolyourtool.api.dto.OfferDTO;
import pl.polsl.io.mytoolyourtool.domain.category.Category;
import pl.polsl.io.mytoolyourtool.domain.category.CategoryRepository;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<OfferDTO> getMyOffers() {
        User user = userService.whoami();
        List<Offer> myOffers= offerRepository.findOffersByLenderId(user.getId());

        if(myOffers.isEmpty())
        {
            throw new EntityNotFoundException("User has no offers.");
        }
        return myOffers.stream().map(OfferDTO::fromDomain).collect(Collectors.toList());
    }

    public OfferDTO getSpecificOffer(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()) {
            throw new EntityNotFoundException("Offer with id:" + offerId + " does not exist.");
        }
        Offer offer = optionalOffer.get();

        return new OfferDTO(offer.getId(),offer.getToolName(),offer.getDescription(),offer.getToolQuality());

    }


}