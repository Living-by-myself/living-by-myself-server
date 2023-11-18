package com.example.livingbymyselfserver.common.entity;

import com.example.livingbymyselfserver.common.entity.TimeStamped;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Like extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
}
