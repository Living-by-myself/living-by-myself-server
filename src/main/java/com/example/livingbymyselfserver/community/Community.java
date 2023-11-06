package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "communities")
@NoArgsConstructor
@DynamicUpdate
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private CommunityCategoryEnum category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
