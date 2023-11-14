package com.example.livingbymyselfserver.attachment.user;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import lombok.Getter;

@Getter
public class S3UserFileResponseDto {
    private final Long id;
    private final String fileName;

    public S3UserFileResponseDto(AttachmentUserUrl attachmentUserUrl) {
        this.id = attachmentUserUrl.getId();
        this.fileName = attachmentUserUrl.getFileName();
    }
}
