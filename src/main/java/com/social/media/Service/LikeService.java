package com.social.media.Service;

import com.social.media.Dao.LikeRepository;
import com.social.media.Entity.Like;
import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public void addLikePost(User user, Post post) {

        if(!likeRepository.existsByUserIdAndPostPostId(user.getId(), post.getPostId())){
        Like like=new Like();
        like.setPost(post);
        like.setUser(user);
        likeRepository.save(like);}
        else {
            throw  new RuntimeException("The User already Like Post");
        }
    }
}
