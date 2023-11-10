package com.example.livingbymyselfserver.attachment.entity;

import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "attachment_user_urls")
@DynamicUpdate
@NoArgsConstructor
public class AttachmentUserUrl extends Attachment{
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public AttachmentUserUrl(User user, String filename) {
        super.fileName = filename;
        this.user = user;
    }
}
