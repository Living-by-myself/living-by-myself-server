package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);

    int countAllByUser(User user);
}
