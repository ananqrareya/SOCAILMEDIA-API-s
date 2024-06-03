package com.social.media.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id ;
    @Column(name = "first_name")
    private String firstName ;
    @Column(name="last_name")
    private String lastName;
    @Column(name = "email")
    private String email ;
    @Column(name = "username")
    private String userName;
    @Column(name = "passwords")
    private String passwords;
    @OneToMany(mappedBy = "follower")
    @JsonManagedReference
    private Set<Follow> following;

        @OneToMany(mappedBy = "followee")
    @JsonManagedReference
    private Set<Follow> followers;
    public User(String firstName, String lastName, String email, String userName, String passwords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.passwords = passwords;
    }

    public User(int id) {
        this.id = id;
    }
}
