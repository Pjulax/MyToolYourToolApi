package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.polsl.io.mytoolyourtool.domain.category.Category;

@Getter
@AllArgsConstructor
public class CategoryDTO {
    private final Long id;
    private final String name;
    private final Integer count;

    public static CategoryDTO fromModel(Category category){
        return new CategoryDTO(category.getId(),category.getName(),5);
    }
}
