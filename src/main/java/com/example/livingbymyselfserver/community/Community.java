package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.comment.entity.CommunityComment;
import com.example.livingbymyselfserver.common.entity.TimeStamped;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.like.entity.CommunityLike;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "communities")
@NoArgsConstructor
@DynamicUpdate
public class Community extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private CommunityCategoryEnum category;

    @Column(nullable = false)
    private int viewCnt = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<CommunityLike> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<CommunityComment> commentList = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(CommunityCategoryEnum category) {
        this.category = category;
    }

    public Community(CommunityRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.category = requestDto.getCategory();
        this.user = user;
    }
}
