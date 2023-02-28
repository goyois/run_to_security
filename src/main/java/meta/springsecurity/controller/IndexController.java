package meta.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import meta.springsecurity.entity.User;
import meta.springsecurity.reposotory.UserRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping({ "", "/"})
    public @ResponseBody String index() {
        return "인덱스 페이지입니다.";
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


    //관리자 전용 메뉴 생성방법 1번
    //하나만 걸고 싶다라면
    @Secured("ROLE_ADMIN")  //들어가지던 인포가 안들어가짐  = 특장 메서드에 조건을 걸어 ADMIN만 접근할 수 있음
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }


    //관리자 전용 메누 생성방법 2번
    //두가지 이상의 직급의 사람들을 걸고 싶다
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")//들어가지던 인포가 안들어가짐  = 특장 메서드에 조건을 걸어 ADMIN만 접근할 수 있음
    //@PostAuthorize 이 어노테이션도 있으나 PreAuthorize에서 다 처리가능
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
