package com.example.livingbymyselfserver.attachment.community;

import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import lombok.Getter;

@Getter
public class S3CommunityFileResponseDto {
    private String fileName;
    private Long id;

    public S3CommunityFileResponseDto(AttachmentCommunityUrl s3CommunityFile) {
        this.id = s3CommunityFile.getId();
        this.fileName = s3CommunityFile.getFileName();
    }
}
