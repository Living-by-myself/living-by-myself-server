package com.example.livingbymyselfserver.groupBuying.application;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "application_users")
@NoArgsConstructor
@Getter
public class ApplicationUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_buying_id")
    private  GroupBuying groupBuying;

    private ReceivingGoodsEnum receivingGoodsEnum;

    public void setReceivingGoodsEnum(ReceivingGoodsEnum receivingGoodsEnum){
        this.receivingGoodsEnum = receivingGoodsEnum;
    }

    public ApplicationUsers(User user, GroupBuying groupBuying){
        this.user = user;
        this.groupBuying = groupBuying;
        receivingGoodsEnum = ReceivingGoodsEnum.DO_NOT_RECEIVING_GOODS;
    }
}
