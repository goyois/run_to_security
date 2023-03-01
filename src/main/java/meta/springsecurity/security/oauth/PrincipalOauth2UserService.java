package meta.springsecurity.security.oauth;

import lombok.RequiredArgsConstructor;
import meta.springsecurity.entity.User;
import meta.springsecurity.reposotory.UserRepository;
import meta.springsecurity.security.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;  //해당 아이디로 회원가입이 되어 있는지를 위함

    public PrincipalOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest :" +userRequest);
        System.out.println("getAccessToken :" +userRequest.getAccessToken());
        System.out.println("getClientRegistration :" +userRequest.getClientRegistration()); //registration 로 어떤 oauth 를 통해 로그인했는지 확인가능
        System.out.println("getAttributes :" +super.loadUser(userRequest).getAttributes());

        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("getAttributes :" + oauth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oauth2User.getAttribute("sub");
        String username = provider+"_"+providerId; //google_111833099876978461700
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oauth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity,oauth2User.getAttributes());
    }
}


//getAttributes 를 통해서 가져올 수 있는 정보 /오어스 로그인 시키는 과정

//어트리뷰트 정보로 강제 로그인시키는 과정
//username = google_111833099876978461700
//password =  "암호화패스워드"
//email = bau12288@gmail.com
//role = "ROLE_USER"
//provider = "google"
//providerId =111833099876978461700



//구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code를 리턴(OAuth-client라이브러리) -> AccessToken 요청
//userRequest 정보 -> loadUser함수 호충 -> 구글로부터 회원프로필을 받아준다
