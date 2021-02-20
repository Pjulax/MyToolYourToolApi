package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.ReviewDTO;
import pl.polsl.io.mytoolyourtool.domain.user.User;
import pl.polsl.io.mytoolyourtool.domain.user.UserService;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectDoesNotExistException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public List<ReviewDTO> getReviews() {
        User user = userService.whoami();
        List<Review> reviews = reviewRepository.findByReviewedUserId(user.getId())
                .orElseThrow(()-> new ObjectDoesNotExistException("User has no reviews."));
        return reviews.stream().map(ReviewDTO::fromDomain).collect(Collectors.toList());
    }
}
