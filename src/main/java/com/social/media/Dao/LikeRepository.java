package com.social.media.Dao;

import com.social.media.Entity.Like;
import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Integer> {
    boolean existsByUserIdAndPostPostId(int userId,int postId);
    Optional<Like> findByUserAndPost(User user, Post post);
    void deleteByPost(Post post);
}
