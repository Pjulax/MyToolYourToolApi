package pl.polsl.io.mytoolyourtool.domain.review;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.AddReviewDTO;
import pl.polsl.io.mytoolyourtool.api.dto.AverageRatingDTO;
import pl.polsl.io.mytoolyourtool.api.dto.ReviewDTO;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferRepository;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;
import pl.polsl.io.mytoolyourtool.domain.reservation.ReservationRepository;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserRepository;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final OfferRepository offerRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<ReviewDTO> getMyReviews() {
        User user = userService.whoami();
        List<Review> reviews = reviewRepository.findByReviewedUserId(user.getId())
                .orElseThrow(()-> new EntityNotFoundException("User has no reviews."));
        return reviews.stream().map(ReviewDTO::fromDomain).collect(Collectors.toList());
    }

    public void addReview(AddReviewDTO addReviewDTO) {
        if (addReviewDTO.getRating() == null || addReviewDTO.getOpinion() == null
                || addReviewDTO.getReservationId() == null)
        {
            throw new IllegalArgumentException("Provided bad data for new review.");
        }
        else {
            User reviewer = userService.whoami();
            Reservation reviewedReservation = reservationRepository.findById(addReviewDTO.getReservationId())
                    .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + addReviewDTO.getReservationId() + " does not exist."));

            if(!reviewedReservation.getOffer().isReturned())
                throw new IllegalArgumentException("User cannot review while tool is not returned.");

            User reviewedUser;
            Long reviewedUserId;
            if (reviewer.getId().equals(reviewedReservation.getBorrower().getId())) {
                //reviewer is  borrower
                reviewedUserId = reviewedReservation.getOffer().getLender().getId();
                reviewedUser = userRepository.findById(reviewedUserId)
                                            .orElseThrow(() -> new EntityNotFoundException("User with id: " + reviewedUserId + " does not exist."));
                reviewedReservation.getOffer().setLenderReviewed(true);
            } else {
                //reviewer is  lender
                reviewedUserId = reviewedReservation.getBorrower().getId();
                reviewedUser = userRepository.findById(reviewedReservation.getBorrower().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("User with id: " + reviewedUserId + " does not exist."));
                reviewedReservation.getOffer().setBorrowerReviewed(true);
            }
            if(reviewedReservation.getOffer().isLenderReviewed() && reviewedReservation.getOffer().isBorrowerReviewed())
                reviewedReservation.setFinished(true);
            reviewedReservation = reservationRepository.save(reviewedReservation);
            offerRepository.save(reviewedReservation.getOffer());

            Review newReview = Review.builder()
                    .opinion(addReviewDTO.getOpinion())
                    .rating(addReviewDTO.getRating())
                    .reviewer(reviewer)
                    .reviewedUser(reviewedUser)
                    .build();
            reviewRepository.save(newReview);
        }
    }

    public Double calculateUsersAverageRating(Long userId)
    {
        Optional<List<Review>> myOptionalReviews = reviewRepository.findByReviewedUserId(userId);
        if(myOptionalReviews.isEmpty())
        {
            return null;
        }
        List<Review>myReviews = myOptionalReviews.get();
        Double average =  myReviews.stream().map(Review::getRating).mapToDouble(Double::doubleValue).average().getAsDouble();
        average = (double) (Math.round(average * 2))/2;
        return average;
    }

    public AverageRatingDTO calculateMyScore() {
        User user = userService.whoami();
        Double average = calculateUsersAverageRating(user.getId());
        return new AverageRatingDTO(average);
    }
}
