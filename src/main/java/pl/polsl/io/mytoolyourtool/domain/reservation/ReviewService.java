package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.AddReviewDTO;
import pl.polsl.io.mytoolyourtool.api.dto.ReviewDTO;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserRepository;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectDoesNotExistException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<ReviewDTO> getReviews() {
        User user = userService.whoami();
        List<Review> reviews = reviewRepository.findByReviewedUserId(user.getId())
                .orElseThrow(()-> new ObjectDoesNotExistException("User has no reviews."));
        return reviews.stream().map(ReviewDTO::fromDomain).collect(Collectors.toList());
    }

    public void addReview(String opinion,Float rating,Long reviewedUserId) {
        User reviewer = userService.whoami();
        Optional<User> OptionalReviewedUser = Optional.ofNullable((userRepository.findById(reviewedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + reviewedUserId + " does not exist."))));
        User reviewedUser = OptionalReviewedUser.get();

        Review review = Review.builder()
                .opinion(opinion)
                .rating(rating)
                .reviewer(reviewer)
                .reviewedUser(reviewedUser)
                .build();
        reviewRepository.save(review);

    }
}
