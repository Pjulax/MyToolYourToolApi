package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.AddReviewDTO;
import pl.polsl.io.mytoolyourtool.api.dto.ReviewDTO;
import pl.polsl.io.mytoolyourtool.domain.reservation.ReviewService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(produces = "application/json")
    public List<ReviewDTO> getReviews()
    {
        return reviewService.getReviews();
    }

    @PostMapping
    public void addReview(@RequestBody AddReviewDTO review)
    {
        reviewService.addReview(review.getOpinion(),review.getRating(),review.getReviewedUserId());
    }
}
