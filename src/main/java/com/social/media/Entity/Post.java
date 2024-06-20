package com.social.media.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;
    @Column(name = "content")
    private String content ;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt ;
    @Column(name = "last_modified", nullable = false)
    @UpdateTimestamp
    private Timestamp lastModified;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userPosts;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Like>likesPost;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Comments>comments;
}
