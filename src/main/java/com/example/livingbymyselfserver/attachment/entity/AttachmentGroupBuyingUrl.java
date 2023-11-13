package com.example.livingbymyselfserver.attachment.entity;

import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
