package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.reservation.Review;

@Getter
@AllArgsConstructor
public class ReviewDTO {
    private final String opinion;
    private final Float rating;

    public static ReviewDTO fromDomain(Review review){
        return new ReviewDTO(review.getOpinion(),review.getRating());
    }
}
