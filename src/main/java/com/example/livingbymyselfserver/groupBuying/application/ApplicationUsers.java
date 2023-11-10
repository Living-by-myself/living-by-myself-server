package com.example.livingbymyselfserver.groupBuying.application;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ApplicationUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name = "group_buying_id")
    protected GroupBuying groupBuying;

    public ApplicationUsers(User user, GroupBuying groupBuying){
        this.user = user;
        this.groupBuying = groupBuying;
    }
}
