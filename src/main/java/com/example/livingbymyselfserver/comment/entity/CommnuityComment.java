package com.example.livingbymyselfserver.comment.entity;

import com.example.livingbymyselfserver.comment.dto.CommentRequestDto;
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
@Table(name = "community_comments")
@DynamicUpdate
@NoArgsConstructor
public class CommnuityComment extends Comment {
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public CommnuityComment(CommentRequestDto requestDto, User user, Community community) {
        super.description = requestDto.getDescription();
        super.user = user;
        this.community = community;
    }
}
