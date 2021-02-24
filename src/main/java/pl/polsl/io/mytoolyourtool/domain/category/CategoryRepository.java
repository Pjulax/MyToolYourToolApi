package pl.polsl.io.mytoolyourtool.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);

    Category findByOffersContains(Offer offer);
}
