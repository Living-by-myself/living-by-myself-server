package com.example.livingbymyselfserver.like.entity;

import com.example.livingbymyselfserver.common.entity.TimeStamped;
import com.example.livingbymyselfserver.fairs.GroupBuying;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "group_buying_pick_like")
@DynamicUpdate
@NoArgsConstructor
public class GroupBuyingPickLike extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name = "group_buying_id")
    private GroupBuying groupBuying;

    public GroupBuyingPickLike(User user, GroupBuying groupBuying) {
        this.user = user;
        this.groupBuying = groupBuying;
    }
}
