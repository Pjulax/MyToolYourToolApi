package pl.polsl.io.mytoolyourtool.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @GetMapping
    public String hello(){
        return "Hello at mytoolyourtool categories api";
    }
}
