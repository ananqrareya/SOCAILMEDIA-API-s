package com.social.media.Dao;

import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Integer> {
Set<Post>findAllByUserPosts(User user);

}
