package com.social.media.Dao;

import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
  Optional<User> findByUserName(String username);
  boolean existsByEmail(String email);
 User findByPosts(Post post);
 boolean existsByPosts(User user);


}
