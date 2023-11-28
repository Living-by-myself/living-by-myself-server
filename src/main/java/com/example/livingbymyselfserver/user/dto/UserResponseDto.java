package com.example.livingbymyselfserver.user.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String nickname;
    private String profileImage;
    private String address;

    public UserResponseDto(User user, AttachmentUserUrl attachmentUserUrl) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImage = attachmentUserUrl.getFileName();
        this.address = user.getAddress();
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.address = user.getAddress();
    }
}
