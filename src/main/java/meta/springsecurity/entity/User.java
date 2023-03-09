package meta.springsecurity.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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


    @Builder
    public User(String username, String password, String email, String role, Timestamp createDate, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
        this.provider = provider;
        this.providerId = providerId;
    }
}
