package com.example.livingbymyselfserver.community.comment;

import com.example.livingbymyselfserver.community.comment.dto.CommentRequestDto;
import com.example.livingbymyselfserver.common.entity.Comment;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.like.entity.CommunityCommentLike;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "community_comments")
@DynamicUpdate
@NoArgsConstructor
public class CommunityComment extends Comment {
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(mappedBy = "communityComment", cascade = CascadeType.REMOVE)
    private List<CommunityCommentLike> communityCommentLikeList = new ArrayList<>();

    public CommunityComment(CommentRequestDto requestDto, User user, Community community) {
        super.description = requestDto.getDescription();
        super.user = user;
        this.community = community;
    }
}
