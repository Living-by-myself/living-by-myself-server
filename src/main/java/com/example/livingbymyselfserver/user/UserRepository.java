package com.example.livingbymyselfserver.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
  Optional<User> findByPhoneNumber(String phoneNumber);

  boolean existsByPhoneNumber(String phoneNumber);

  List<User> findByIdIn(List<Long> userIdList);

}
