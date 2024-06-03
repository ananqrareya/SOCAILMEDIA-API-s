    package com.social.media.RestController;

    import com.social.media.Dto.UserSummaryDTO;
    import com.social.media.Entity.Follow;
    import com.social.media.Entity.User;
    import com.social.media.Service.FollowService;
    import com.social.media.Service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.Set;

    @RestController
        @RequestMapping("/api/social")
    public class SocialStream {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;
        @PostMapping("/user/follow/{followeeId}")

        public ResponseEntity<String> followUser(@PathVariable int followeeId){
            User follower =userService.getAuthenticatedUser();
            User followee=userService.findById(followeeId);
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
    }
