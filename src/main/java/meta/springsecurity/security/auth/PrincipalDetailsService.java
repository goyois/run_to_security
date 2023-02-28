package meta.springsecurity.security.auth;

import lombok.RequiredArgsConstructor;
import meta.springsecurity.entity.User;
import meta.springsecurity.reposotory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessiongUrl("/login"); 로 걸어놨가때문에
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행

@Service  //PrincipalDetails는 왜 안붙이는가 ? => 추후 강제로 띄울것임
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //시큐리티 session(내부에 Authentication이 들어감(내부에 UserDetails 들어감)) 안에 들어가는 형태
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : "+username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
