package pl.polsl.io.mytoolyourtool.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.CategoryDTO;
import pl.polsl.io.mytoolyourtool.domain.category.CategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello at mytoolyourtool categories api";
    }

    @GetMapping( produces = "application/json")
    public List<CategoryDTO> getAll(){
        return categoryService.getAll();
    }

    @PostMapping
    public void addNew(@RequestBody CategoryDTO category){
        categoryService.addNewCategory(category.getName());
    }

}
