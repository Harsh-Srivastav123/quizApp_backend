package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    //    @Email(message = "Not a valid email id")
    String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    String profileUrl;
    int totalMarks;
    Integer userRank;
    Integer totalQuiz;
    @OneToMany(cascade =CascadeType.ALL,mappedBy = "user")
    List<Result> resultList;


    @OneToMany
    @JoinColumn(name = "userId",referencedColumnName = "id")
    List<Session> sessionList;

    @JsonIgnore
    boolean isEnabled;

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
        return this.isEnabled;
    }
}
