package com.example.livingbymyselfserver.attachment.entity;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "attachment_groupBuying_urls")
@DynamicUpdate
@NoArgsConstructor
public class AttachmentGroupBuyingUrl extends Attachment{

    @OneToOne
    @JoinColumn(name = "group_buying_id")
    private GroupBuying groupBuying;

    public AttachmentGroupBuyingUrl(String filename, GroupBuying groupBuying) {
        super.fileName = filename;
        this.groupBuying = groupBuying;
    }
}
