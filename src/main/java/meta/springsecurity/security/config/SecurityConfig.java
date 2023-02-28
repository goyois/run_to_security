package meta.springsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration  //IoC컨테이너에 빈으로 등록
@EnableWebSecurity  //활성화시키는 어노테이션 = 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
//@EnableGlobalMethodSecurity(securedEnabled = true)  //컨트롤러의 secured 어노테이션 활성화 1번 방법
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //preAuthorize 어노테이션까지 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{


    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()  //인증만 되면 들어갈 수 있는 주소 - 4강
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //admin or manager or user 가능
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")  //admin or user 가능
                .anyRequest().permitAll()  //위 세개의 주소가 아닌 이상 다른 요청은 전부 권한 허용
                .and() //  1-1
                .formLogin()// 1-2
                .loginPage("/loginForm")  // 1-3 위 설정 추가로 로그인이 필요한 권한의 url로 접속 시 로그인패이지가 나옴 /2강
                .loginProcessingUrl("/login")  //login주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행하므로 별도의 로그인 컨트롤러가 필요없음
                .defaultSuccessUrl( "/");
    }
}
