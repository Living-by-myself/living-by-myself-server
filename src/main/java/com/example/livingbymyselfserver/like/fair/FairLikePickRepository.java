package com.example.livingbymyselfserver.like.fair;

import com.example.livingbymyselfserver.fairs.Fair;
import com.example.livingbymyselfserver.like.entity.FairLikePick;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FairLikePickRepository extends JpaRepository<FairLikePick, Long> {
    Boolean existsByFairAndUser(Fair fair, User user);

    FairLikePick findByFairAndUser(Fair fair, User user);
}
