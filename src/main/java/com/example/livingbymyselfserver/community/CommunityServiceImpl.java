package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    @Override
    public ApiResponseDto createCommunity(User user, CommunityRequestDto requestDto) {
        Community community = new Community(requestDto, user);
        communityRepository.save(community);

        return new ApiResponseDto("커뮤니티 게시글 생성 완료", 201);
    }

    @Override
    @Transactional
    public ApiResponseDto updateCommunity(User user, Long communityId, CommunityRequestDto requestDto) {
        Community community = findCommunity(communityId);

        communityUserVerification(community, user);

        community.setTitle(requestDto.getTitle());
        community.setDescription(requestDto.getDescription());
        community.setCategory(requestDto.getCategory());

        return new ApiResponseDto("커뮤니티 게시글 수정 완료", 200);
    }

    @Override
    @Transactional
    public ApiResponseDto deleteCommunity(User user, Long communityId) {
        Community community = findCommunity(communityId);
        communityUserVerification(community, user);

        communityRepository.delete(community);

        return new ApiResponseDto("커뮤니티 게시글 삭제", 200);
    }

    @Override
    public CommunityDetailResponseDto getCommunityDetailInfo(Long communityId) {
        Community community = findCommunity(communityId);

        return new CommunityDetailResponseDto(community);
    }

    public Community findCommunity(Long id) {
        return communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
    }

    private void communityUserVerification(Community community, User user) {
        if (!community.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글 작성한 유저가 아닙니다");
        }
    }
}
