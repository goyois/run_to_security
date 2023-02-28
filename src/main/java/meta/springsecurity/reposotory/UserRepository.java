package meta.springsecurity.reposotory;

import meta.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//@Repository가 없어도 IoC에 빈으로 잡힘 = JpaRepository를 상속했기때문
public interface UserRepository extends JpaRepository<User, Integer> {

    // select * from user  where username = ? (파라미터로 들어오는 값 (username ))
    public User findByUsername(String username);  //JPA Query method

}



//5강 role 변경 sql
//uodate user set role = 'ROLE_MANAGER' where id = 4;
//update user set role = 'ROLE_ADMIN' where id = 5;
//commit;