package com.example.livingbymyselfserver.like.entity;

import com.example.livingbymyselfserver.fairs.Fair;
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
@Table(name = "fair_likes_pick")
@DynamicUpdate
@NoArgsConstructor
public class FairLikePick extends Like {
    @ManyToOne
    @JoinColumn(name = "fair_id")
    private Fair fair;

    public FairLikePick(User user, Fair fair) {
        super.user = user;
        this.fair = fair;
    }
}
