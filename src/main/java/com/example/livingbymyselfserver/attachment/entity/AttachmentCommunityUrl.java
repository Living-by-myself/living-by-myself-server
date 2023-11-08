package com.example.livingbymyselfserver.attachment.entity;

import com.example.livingbymyselfserver.community.Community;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "attachment_community_urls")
@DynamicUpdate
@NoArgsConstructor
public class AttachmentCommunityUrl extends Attachment {

    @OneToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public AttachmentCommunityUrl (String filename, Community community) {
        super.fileName = filename;
        this.community = community;
    }
}
