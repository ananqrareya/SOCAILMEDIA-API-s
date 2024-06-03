package com.social.media.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "follow")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    @JsonBackReference
    private User follower;
    @ManyToOne
    @JoinColumn(name = "followee_id")
    @JsonBackReference
    private User followee ;

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
