package com.social.media.Dao;

import com.social.media.Entity.Comments;
import com.social.media.Entity.Like;
import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
    Optional<Comments> findByUserAndPost(User user, Post post);
}
