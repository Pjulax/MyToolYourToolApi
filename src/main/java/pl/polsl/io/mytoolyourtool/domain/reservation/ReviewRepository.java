package pl.polsl.io.mytoolyourtool.domain.reservation;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r from Review r where r.reviewedUser.id=?1")
    Optional<List<Review>> findByReviewedUserId(Long id);
}
