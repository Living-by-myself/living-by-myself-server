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
public abstract class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "title", nullable = false)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    public void setDescription(String description) {
        this.description = description;
    }
}
