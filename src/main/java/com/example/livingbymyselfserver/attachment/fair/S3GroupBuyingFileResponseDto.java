package com.example.livingbymyselfserver.attachment.fair;

import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import lombok.Getter;

@Getter
public class S3GroupBuyingFileResponseDto {
    private final Long id;
    private final String fileName;

    public S3GroupBuyingFileResponseDto(AttachmentGroupBuyingUrl attachmentGroupBuyingUrl) {
        this.id = attachmentGroupBuyingUrl.getId();
        this.fileName = attachmentGroupBuyingUrl.getFileName();
    }
}
