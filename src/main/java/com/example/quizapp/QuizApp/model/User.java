package com.example.quizapp.QuizApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User  implements UserDetails , Comparable<User>  {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)

    Integer id;
    String userName;
    // only for verification
    String email;
    String password;
    String profileUrl;
    int totalMarks;
    Integer userRank;
    Integer totalQuiz;
    @OneToMany(cascade =CascadeType.ALL,mappedBy = "user")
    List<Result> resultList;

    @Override
    public int compareTo(User o) {
        return o.totalMarks-this.totalMarks;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
