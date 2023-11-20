package com.example.livingbymyselfserver.attachment.user;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentUserUrlRepository extends JpaRepository<AttachmentUserUrl, Long> {
    AttachmentUserUrl findByUser(User user);

    void deleteByUser(User user);
}
