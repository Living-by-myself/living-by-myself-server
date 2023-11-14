package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByUserAndBadgeEnum (User user, BadgeEnum badge);

    List<Badge> findAllByUser (User user);
}
