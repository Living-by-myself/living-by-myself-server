package com.example.livingbymyselfserver.fairs.application;

import com.example.livingbymyselfserver.fairs.Fair;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUsersRepository extends JpaRepository<ApplicationUsers,Long> {
    Boolean existsByFairAndUser(Fair fair, User user);

    ApplicationUsers findByFairAndUser(Fair fair, User user);
}
