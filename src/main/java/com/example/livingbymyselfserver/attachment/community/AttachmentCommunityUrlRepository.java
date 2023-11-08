package com.example.livingbymyselfserver.attachment.community;

import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentCommunityUrlRepository extends JpaRepository<AttachmentCommunityUrl, Long> {
    AttachmentCommunityUrl findByCommunity(Community community);

    void deleteByCommunity(Community community);
}
