package pl.polsl.io.mytoolyourtool.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.polsl.io.mytoolyourtool.domain.review.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r from Review r where r.reviewedUser.id=?1")
    Optional<List<Review>> findByReviewedUserId(Long id);

    Optional<List<Review>> findByReviewer_Id(Long id);
}
