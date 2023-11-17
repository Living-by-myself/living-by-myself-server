package com.example.livingbymyselfserver.community.like.entity;


import com.example.livingbymyselfserver.common.entity.Like;
import com.example.livingbymyselfserver.community.comment.CommunityComment;
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
@Table(name = "community_comment_likes")
@DynamicUpdate
@NoArgsConstructor
public class CommunityCommentLike extends Like {
    @ManyToOne
    @JoinColumn(name = "community_comment_id")
    private CommunityComment communityComment;

    public CommunityCommentLike(User user, CommunityComment comment) {
        super.user = user;
        this.communityComment = comment;
    }
}
