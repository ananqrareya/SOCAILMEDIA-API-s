package com.social.media.Service;

import com.social.media.Dao.FollowRepository;
import com.social.media.Dao.LikeRepository;
import com.social.media.Dao.PostRepository;
import com.social.media.Dto.*;

import com.social.media.Entity.Follow;
import com.social.media.Entity.Like;
import com.social.media.Entity.Post;
import com.social.media.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
@Autowired
    private PostRepository postRepository;
@Autowired
private FollowRepository followRepository;
@Autowired
private LikeRepository likeRepository;
@Autowired
private FileStorageService fileStorageService;

    public Post addPost(User user, NewPostDTO newPostDTO) {
        Post post=new Post();
        post.setContent(newPostDTO.getContent());
        post.setUserPosts(user);
        return postRepository.save(post);
    }
    public Post createPostMedia(User user,String content, MultipartFile file){
Post post=new Post();
        post.setUserPosts(user);
        post.setContent(content);

        if (file != null && !file.isEmpty()) {
            String filename = fileStorageService.store(file);
            post.setFilePath(filename);
        }
        return postRepository.save(post);


    }
    public Set<UserPostDTO>getPostByFollowee(User user) {
        Set<Follow> followingList = followRepository.findByFollower(user);
        return followingList.stream()
                .map(Follow::getFollowee)
                .filter(followee -> followee.getPosts() != null && !followee.getPosts().isEmpty())
                .map(followee -> {
                    List<PostDto> posts = followee.getPosts().stream().map(post ->
                            new PostDto(
                                    post.getPostId(),
                                    post.getContent(),
                                    post.getCreatedAt(),
                                    post.getLikesPost().size(),
                                    post.getLikesPost().stream()
                                            .map(like -> new LikeDto(like.getLikeId(), like.getUser().getUserName()))
                                            .collect(Collectors.toList()),
                                    post.getComments().stream().map(comments -> new CommentDto(comments.getComment(),comments.getUser().getUserName())).collect(Collectors.toList())
                            )
                    ).collect(Collectors.toList());

                    return new UserPostDTO(followee.getUserName(), posts);
                })
                .collect(Collectors.toSet());
    }
    public Post findById(int id){
        Post post=postRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found Post :"+id));
        return post;
    }
    public void deletePost(User user,int postId){
        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Not Found Post"));
        if(!post.getUserPosts().equals(user)){
            throw new RuntimeException("You are not authorized to delete this post");
        }
        List<Like>likes=post.getLikesPost().stream().toList();
        likes.forEach(like -> likeRepository.delete(like));
        postRepository.delete(post);
    }

    public Post updatePost(User user, PostDto postDto) {
        Post post=postRepository.findById(postDto.getId()).orElseThrow(()->new RuntimeException("Not Found Post"));
        if(!post.getUserPosts().equals(user)){
            throw new RuntimeException("You are not authorized to update this post");
        }
        post.setContent(postDto.getContent());
      return   postRepository.save(post);
    }
}
