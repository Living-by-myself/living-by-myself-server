package com.example.livingbymyselfserver.attachment.fair;

import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentGroupBuyingUrlRepository extends JpaRepository<AttachmentGroupBuyingUrl, Long> {
    AttachmentGroupBuyingUrl findByGroupBuying(GroupBuying groupBuying);

    void deleteByGroupBuying(GroupBuying groupBuying);
}
