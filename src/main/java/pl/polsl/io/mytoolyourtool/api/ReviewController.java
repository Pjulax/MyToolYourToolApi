package pl.polsl.io.mytoolyourtool.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.AddReviewDTO;
import pl.polsl.io.mytoolyourtool.api.dto.ReviewDTO;
import pl.polsl.io.mytoolyourtool.domain.review.ReviewService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(produces = "application/json")
    public List<ReviewDTO> getMyReviews()
    {
        return reviewService.getMyReviews();
    }

    @GetMapping(path = "/score", produces = "application/json")
    public Double calculateScore(){ return reviewService.calculateScore();}

    @PostMapping
    public void addReview(@RequestBody AddReviewDTO addReviewDTO)
    {
        reviewService.addReview(addReviewDTO);
    }
}
