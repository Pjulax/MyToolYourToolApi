package pl.polsl.io.mytoolyourtool.domain.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.CategoryDTO;
import pl.polsl.io.mytoolyourtool.api.dto.OfferDTO;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectExistsException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addNewCategory(String name) {
        if (categoryRepository.findByName(name).isEmpty()) {
            Category category = new Category();
            category.setName(name);
            categoryRepository.save(category);
        } else {
            throw new ObjectExistsException("Category with name '" + name + "' already exists.");
        }
    }

    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::fromModel).collect(Collectors.toList());
    }

    public List<OfferDTO> getCategoryOffers(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " doesn't exists."));
        return category.getOffers().stream().filter(o -> !o.isReservationChosen()).map(OfferDTO::fromDomain).collect(Collectors.toList());
    }
}
