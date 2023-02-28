package meta.springsecurity.security.auth;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행하는데
// 로그인진행이 완료가 돠면 session에 넣어주는데 시큐리티는 별도의 session을 가지고 있음(Security ContextHolder 라는 키값에 세션을 저장해줌)
// 오브젝트 => authentication 타입 객체
// Authentication 안에 User 정보가 있어야함
// User 오블젝트타입 => UserDetails 타입 객체

//Security Session 에 있는 세션정보를 겟해서 꺼내면 Authentication 객체가 나오는데 이 안에서 UserDetails(PrincipalDetails)를 꺼내면 User오브젝트에 접근 가능한다


import lombok.RequiredArgsConstructor;
import meta.springsecurity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class PrincipalDetails  implements UserDetails {

    private User user;  // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority(){ //collect이 받을 수 있는 타입이 GrantedAuthority 이므로 매개변수로 넣어줌
            @Override
            public String getAuthority() {  //해당 방법으로 String 타입의 Authority를 받을 수 있고 Role 을 가져올 수 있음
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //계정 만료되었는가?
        return true; //아니요
    }

    @Override
    public boolean isAccountNonLocked() { //계정 잠겼는가
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //계정의 비밀번호 유효기간이 지났는가
        return true;
    }

    @Override
    public boolean isEnabled() {  //활성화되었는가
        return true;
    }


    //위 조건값에 대하여
    // ex 우리 사이트에서 1년동안 로그인을 안하면 휴면 계정으로 하기로함
    // 현재시간 - 로그인시간 => 1년을 초과하면 return false;
}
