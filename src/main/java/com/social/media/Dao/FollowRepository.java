package com.social.media.Dao;

import com.social.media.Entity.Follow;
import com.social.media.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FollowRepository extends JpaRepository<Follow,Integer> {
    boolean existsByFollowerIdAndFolloweeId(int followerId, int followeeId);
    Follow findByFollowerAndFollowee(User follower,User followee);
    Set<Follow> findByFollowee(User user);
    Set<Follow>findByFollower(User user);


}
