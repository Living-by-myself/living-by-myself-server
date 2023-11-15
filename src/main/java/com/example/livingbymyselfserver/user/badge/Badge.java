package com.example.livingbymyselfserver.user.badge;

import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "badges")
@NoArgsConstructor
@DynamicUpdate
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private BadgeEnum badgeEnum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Badge(BadgeEnum badgeEnum,User user) {
        this.badgeEnum = badgeEnum;
        this.user = user;
    }
}
