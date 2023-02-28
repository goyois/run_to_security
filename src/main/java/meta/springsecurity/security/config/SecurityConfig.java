package meta.springsecurity.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  //IoC컨테이너에 빈으로 등록
@EnableWebSecurity  //활성화시키는 어노테이션 = 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()  //위 세개의 주소가 아닌 이상 다른 요청은 전부 권한 허용
                .and() //  1-1
                .formLogin()// 1-2
                .loginPage("/login");  // 1-3 위 설정 추가로 로그인이 필요한 권한의 url로 접속 시 로그인패이지가 나옴 /2강
    }
}