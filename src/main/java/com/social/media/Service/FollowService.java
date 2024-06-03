package com.social.media.Service;

import com.social.media.Dao.FollowRepository;
import com.social.media.Dao.UserRepository;
import com.social.media.Entity.Follow;
import com.social.media.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
@Autowired
    private FollowRepository followRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private UserService userService;
    public Follow followUser(User follower ,User followee) {

      if(!(followRepository.existsByFollowerIdAndFolloweeId(follower.getId(),followee.getId()))) {
          return   followRepository.save(new Follow(follower, followee));
      }

        return null ;
    }
    public void unFollowUser(Follow follow){
            followRepository.delete(follow);
    }
    public Follow findByFollowerAndFollowee(User follower ,User followee){
        return followRepository.findByFollowerAndFollowee(follower,followee);
    }
}
