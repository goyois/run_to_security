package meta.springsecurity.entity;


import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {
    @Id  //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String email;
    private String role;  //ROLE_ADMIN, ROLE_USER
    @CreationTimestamp
    private Timestamp createDate;
    //private Timestamp loginDate;  //마지막 로그인한 날짜

    private String provider;
    private String providerId;

}
