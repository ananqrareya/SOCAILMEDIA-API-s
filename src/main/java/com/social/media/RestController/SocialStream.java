    package com.social.media.RestController;

    import com.social.media.Dto.*;
    import com.social.media.Entity.Follow;
    import com.social.media.Entity.Post;
    import com.social.media.Entity.User;
    import com.social.media.Service.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.io.UrlResource;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.Collection;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;
    import java.util.stream.Collectors;

    @RestController
        @RequestMapping("/api/social")
    public class SocialStream {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentsService commentsService;

        @PostMapping("/user/follow/{followeeId}")

        public ResponseEntity<String> followUser(@PathVariable int followeeId){
            User follower =userService.getAuthenticatedUser();
            User followee=userService.findById(followeeId);
            if(follower.getId()==followee.getId()){
                throw new RuntimeException("Oops you will follow yourself");
            }
           Follow follow=followService.followUser(follower,followee);
           if(follow==null){
               throw  new RuntimeException("The User already Follow");
           }
            return new ResponseEntity<>("The user "+" "+follower.getUserName()+" "+"Followed the user "+followee.getUserName(), HttpStatus.OK);
        }
        @DeleteMapping("/user/unfollow/{followeeId}")
        public ResponseEntity<String> unFollowUser(@PathVariable int followeeId){
            User follower=userService.getAuthenticatedUser();
            User followee=userService.findById(followeeId);
             Follow follow=followService.findByFollowerAndFollowee(follower,followee);
            if(follow!=null){
                followService.unFollowUser(follow);
                return new ResponseEntity<>("The user"+" "+follower.getUserName()+" "+"Unfollowed the user "+" "+followee.getUserName(),HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Follow relationship does not exist ",HttpStatus.BAD_REQUEST);
        }
        @GetMapping("/user/followers")
        public ResponseEntity<?> getFollowers(){
            User user=userService.getAuthenticatedUser();
            Set<UserSummaryDTO> followersUser=userService.getFollowers(user);
            if(followersUser.isEmpty()){
                throw new RuntimeException("The User : " + " " +user.getUserName() +" " +"No one is following him" );

            }
            return new ResponseEntity<>( followersUser,HttpStatus.OK);
        }
        @GetMapping("/user/usersIfollow")
        public ResponseEntity<?>getFollowee(){
            User user=userService.getAuthenticatedUser();
            Set<UserSummaryDTO>followeeUser=userService.getFollowees(user);
            if(followeeUser.isEmpty()){
                throw new RuntimeException("The User : "+" "+user.getUserName()+" "+" No one is following ");
            }
            return new ResponseEntity<>(followeeUser,HttpStatus.OK);
        }
        @PostMapping("/user/newPost")
        public ResponseEntity<?>newPost(@RequestBody NewPostDTO postDto){
            User user =userService.getAuthenticatedUser();

           Post post= postService.addPost(user,postDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully: " + post.getContent());

        }
        @PostMapping("/user/newPostMedia")
        public ResponseEntity<?>newPostMedia(@RequestParam("content")String content , @RequestParam("file")
                                             MultipartFile file){
                User user=userService.getAuthenticatedUser();

            Post post = postService.createPostMedia(user, content, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        }
        @GetMapping("/user/feed")
        public ResponseEntity<?>getFeeds(){
            User user=userService.getAuthenticatedUser();
            Set<UserPostDTO>followeeUsers=postService.getPostByFollowee(user);
            if(followeeUsers.isEmpty()||followeeUsers==null){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no new Feed from users");
            }
            return new ResponseEntity<>(followeeUsers,HttpStatus.OK);
        }
        @PostMapping("/user/like/post/{postId}")
        public ResponseEntity<?>likePost(@PathVariable int postId){
            User user=userService.getAuthenticatedUser();
            Post post=postService.findById(postId);
            User userPost=userService.findUserByPost(post);
            likeService.addLikePost(user,post);
            return  ResponseEntity.status(HttpStatus.OK).body("The User :"+" "+user.getUserName()+" "+"Liked This Post :"+" "+post.getContent()+"That The User Published : "+userPost.getUserName());
        }
    @GetMapping("/user/posts")
        public ResponseEntity<?>userPosts(){
                User user=userService.getAuthenticatedUser();
                UserPostDTO userPostDTo=userService.findAllPost(user);
                return ResponseEntity.status(HttpStatus.OK).body(userPostDTo);
    }


    @DeleteMapping("/user/post/{postId}")
    public ResponseEntity<String>deletePost(@PathVariable int postId) {
        User user = userService.getAuthenticatedUser();
        postService.deletePost(user, postId);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }
    @PutMapping("/user/updatePost")
    public ResponseEntity<?>updatePost(@RequestBody PostDto postDto){
            User user=userService.getAuthenticatedUser();
            Post updatePost=postService.updatePost(user,postDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatePost);
    }
    @DeleteMapping("user/unlikePost/{postId}")
        public ResponseEntity<String>unLikePost(@PathVariable int postId){
            User user=userService.getAuthenticatedUser();
            userService.unlikePost(user,postId);
            return ResponseEntity.status(HttpStatus.OK).body("Post unliked successfully");
    }
@PostMapping("user/comment/post/{postId}")
        public ResponseEntity<?> commentPost(@PathVariable int postId,@RequestBody CommentRequest commentRequest){
            User user=userService.getAuthenticatedUser();
            Post post=postService.findById(postId);
            User userPost=userService.findUserByPost(post);
            commentsService.newComment(user,post,commentRequest.getComment());
    return ResponseEntity.status(HttpStatus.OK)
            .body("The User :" + " " + user.getUserName() + " " + "Comment :" + commentRequest.getComment() + " " +
                    "This Post :" + " " + post.getContent() + " " + "That The User Published : " + userPost.getUserName());
}
@DeleteMapping("user/comment/post/{postId}")
        public ResponseEntity<?>deleteComment(@PathVariable int postId){
            User user=userService.getAuthenticatedUser();
    userService.deleteComments(user,postId);
    return ResponseEntity.status(HttpStatus.OK).body("Post Delete Comment successfully");
}

        }





