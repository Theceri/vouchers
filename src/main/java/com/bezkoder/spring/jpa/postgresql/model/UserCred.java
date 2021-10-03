package com.bezkoder.spring.jpa.postgresql.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.security.SecureRandom;

@Entity
@Table(name = "t_user")
public class UserCred {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;


    @Column(name = "email")
    private String email;

    public UserCred() {
    }

    public UserCred(long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        int strength = 10;
        // BCryptPasswordEncoder bCryptPasswordEncoder =
        //         new BCryptPasswordEncoder(strength, new SecureRandom());
        // this.password = bCryptPasswordEncoder.encode(password);
        this.password = password;
        this.email = email;
    }

    public UserCred(String username, String password, String email) {
        int strength = 10;
        // BCryptPasswordEncoder bCryptPasswordEncoder =
        //         new BCryptPasswordEncoder(strength, new SecureRandom());
        // this.password = bCryptPasswordEncoder.encode(password);
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
