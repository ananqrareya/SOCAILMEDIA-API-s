package com.social.media.Service;

import com.social.media.Dao.*;
import com.social.media.Dto.*;
import com.social.media.Entity.*;
import com.social.media.ExceptionHandler.RegisterUserExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;
@Autowired
private CommentsRepository commentsRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder ,FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.followRepository=followRepository;
    }
    public UserDto newUser(UserDto userDto) throws RegisterUserExceptions {
        if (userDto.getPasswords() == null || userDto.getPasswords().isEmpty()) {
            throw new RegisterUserExceptions("Password cannot be null or empty");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new RegisterUserExceptions( "Email already Exists");
        }

        User user =new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setPasswords(passwordEncoder.encode(userDto.getPasswords()));
        userRepository.save(user);
        return userDto;
    }
    public  User getAuthenticatedUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User user =userRepository.findByUserName(username).orElseThrow(()-> new RuntimeException("Not Found User the "));
            return user;
    }
    public User findById( int id){
        User user=userRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found User the :" + id));
        return user ;
    }
    public Set<UserSummaryDTO> getFollowers(User user){
        Set<Follow>followers=followRepository.findByFollowee(user);
        return followers.stream()
                .map(follow -> new UserSummaryDTO(follow.getFollower().getUserName(),follow.getFollower().getEmail())).collect(Collectors.toSet());
    }

    public Set<UserSummaryDTO> getFollowees(User user) {
        Set<Follow>followingList=followRepository.findByFollower(user);
        return followingList.stream().map(follow -> new UserSummaryDTO(follow.getFollowee().getUserName(),follow.getFollowee().getEmail())).collect(Collectors.toSet());
    }
    public User findUserByPost(Post post){
        return userRepository.findByPosts(post);
    }
    public UserPostDTO findAllPost(User user){

        if (user.getPosts()==null||user.getPosts().isEmpty()){
            throw new RuntimeException("The User Not Add any Post");
        }
        return new UserPostDTO(user.getUserName(),user.getPosts().stream().map(post -> new PostDto(post.getPostId(),post.getContent(),post.getCreatedAt(),post.getLikesPost().size(),post.getLikesPost().stream().map(like -> new LikeDto(like.getLikeId(),like.getUser().getUserName())).collect(Collectors.toList()),post.getComments().stream().map(comments -> new CommentDto(comments.getComment(),comments.getUser().getUserName())).collect(Collectors.toList()))).collect(Collectors.toList()));
    }

    public void unlikePost(User user, int postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        Like like=likeRepository.findByUserAndPost(user,post).orElseThrow(()-> new RuntimeException("Like not found"));
        likeRepository.delete(like);
    }

    public void deleteComments(User user, int postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        Comments comment=commentsRepository.findByUserAndPost(user,post).orElseThrow(()-> new RuntimeException("Comment not found"));
        commentsRepository.delete(comment);
    }
}
