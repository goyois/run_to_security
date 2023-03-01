package meta.springsecurity.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest :" +userRequest);
        System.out.println("getAccessToken :" +userRequest.getAccessToken());
        System.out.println("getClientRegistration :" +userRequest.getClientRegistration()); //registration 로 어떤 oauth 를 통해 로그인했는지 확인가능
        System.out.println("getAttributes :" +super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
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
