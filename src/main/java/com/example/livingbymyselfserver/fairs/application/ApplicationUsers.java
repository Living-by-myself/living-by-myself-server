package com.example.livingbymyselfserver.fairs.application;

import com.example.livingbymyselfserver.fairs.Fair;
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
    @JoinColumn(name = "fairs_id")
    protected Fair fair;

    public ApplicationUsers(User user,Fair fair){
        this.user = user;
        this.fair = fair;
    }
}
