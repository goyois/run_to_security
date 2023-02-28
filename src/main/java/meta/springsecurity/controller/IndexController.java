package meta.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import meta.springsecurity.entity.User;
import meta.springsecurity.reposotory.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  // = view를 리턴하겠다
@RequiredArgsConstructor
public class IndexController {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    //로그인 페이지가 뜨지않고 문자열이 반환되나 일단 로그인을 할 수 없음 /2강
    @GetMapping("/loginForm") //로그인 패이지
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")  //회원가입 페이지
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {  //모델 데이터를 사용
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);  //회원가입은 잘 되지만 시큐리티로 로그인 불가 = 암호화해줘야함
        return "redirect:/loginForm";  //redirect 메서드를 통해 loginForm 페이지를 리턴
    }
}
