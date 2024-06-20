package com.social.media.Service;

import com.social.media.Dao.CommentsRepository;
import com.social.media.Entity.Comments;
import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    public void newComment(User user, Post post, String comment) {
        Comments comments=new Comments();
        comments.setComment(comment);
        comments.setPost(post);
        comments.setUser(user);
        commentsRepository.save(comments);
    }
}
