package pl.polsl.io.mytoolyourtool.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/users")
public class UserController {

    @GetMapping
    public String hello(){
        return "Hello at mytoolyourtool users api";
    }
}
