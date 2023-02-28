package meta.springsecurity.reposotory;

import meta.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


//@Repository가 없어도 IoC에 빈으로 잡힘 = JpaRepository를 상속했기때문
public interface UserRepository extends JpaRepository<User, Integer> {

}
