package pl.polsl.io.mytoolyourtool.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.io.mytoolyourtool.api.dto.CategoryDTO;
import pl.polsl.io.mytoolyourtool.api.dto.OfferDTO;
import pl.polsl.io.mytoolyourtool.domain.category.CategoryService;

import javax.websocket.server.PathParam;
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

    @PostMapping(path = "/add-new")
    public void addNew(@RequestBody String name){
        categoryService.addNewCategory(name);
    }

    @GetMapping(path = "/{id}")
    public List<OfferDTO> getCategoryOffers(@PathVariable("id") Long categoryId) {
        return categoryService.getCategoryOffers(categoryId);
    }

}
