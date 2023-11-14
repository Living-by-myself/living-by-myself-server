package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByUserAndBadgeEnum (User user, BadgeEnum badge);
}
