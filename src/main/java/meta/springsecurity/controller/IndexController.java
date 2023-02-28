package meta.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller  // = view를 리턴하겠다
public class IndexController {


    @GetMapping({"","/"})
    public String index() {
        return "index";
    }
}
