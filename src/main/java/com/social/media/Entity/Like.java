package com.social.media.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
public class Like {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    public Like(Post post, User user, Timestamp createdAt) {
        this.post = post;
        this.user = user;
        this.createdAt = createdAt;
    }
}
