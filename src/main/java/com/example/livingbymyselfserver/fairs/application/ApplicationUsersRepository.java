package com.example.livingbymyselfserver.fairs.application;

import com.example.livingbymyselfserver.fairs.GroupBuying;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUsersRepository extends JpaRepository<ApplicationUsers,Long> {
    Boolean existsByGroupBuyingAndUser(GroupBuying groupBuying, User user);

    ApplicationUsers findByGroupBuyingAndUser(GroupBuying groupBuying, User user);
}
