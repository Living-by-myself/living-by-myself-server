package com.example.livingbymyselfserver.community.like.entity;

import com.example.livingbymyselfserver.common.entity.Like;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "community_likes")
@DynamicUpdate
@NoArgsConstructor
public class CommunityLike extends Like {
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public CommunityLike(User user, Community community) {
        super.user = user;
        this.community = community;
    }
}
