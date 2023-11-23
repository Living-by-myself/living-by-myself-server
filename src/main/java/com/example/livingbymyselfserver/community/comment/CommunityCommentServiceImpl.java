package com.example.livingbymyselfserver.community.comment;

import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.attachment.user.AttachmentUserUrlRepository;
import com.example.livingbymyselfserver.community.comment.dto.CommentRequestDto;
import com.example.livingbymyselfserver.community.comment.dto.CommunityCommentResponseDto;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.CommunityService;
import com.example.livingbymyselfserver.community.like.CommunityCommentLikeRepository;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {
    private final CommunityService communityService;
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityCommentLikeRepository communityCommentLikeRepository;
    private final AttachmentUserUrlRepository attachmentUserUrlRepository;
    @Override
    public List<CommunityCommentResponseDto> getCommunityComments(User user, Long communityId, Pageable pageable) {
        Community community = communityService.findCommunity(communityId);
        return communityCommentRepository.findByCommunityOrderByCreatedAtDesc(community,pageable)
                .stream()
                .map(communityComment -> {
                    Boolean existsLike = communityCommentLikeRepository.existsByCommunityCommentAndUser(communityComment, user);
                    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);
                    UserResponseDto userResponseDto;
                    userResponseDto = (attachmentUserUrl != null) ?
                            new UserResponseDto(community.getUser(), attachmentUserUrl) :
                            new UserResponseDto(community.getUser());
                    return new CommunityCommentResponseDto(communityComment, existsLike, userResponseDto);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponseDto createCommunityComment(User user, Long communityId, CommentRequestDto requestDto) {
        Community community = communityService.findCommunity(communityId);

        CommunityComment comment = new CommunityComment(requestDto, user, community);

        communityCommentRepository.save(comment);

        return new ApiResponseDto("커뮤니티 게시글 댓글 생성", 201);
    }

    @Override
    @Transactional
    public ApiResponseDto updateCommunityComment(User user, Long commentId, CommentRequestDto requestDto) {
        CommunityComment comment = findCommunityComment(commentId);
        comment.setDescription(requestDto.getDescription());

        communityCommentUserVerification(comment, user);

        return new ApiResponseDto("댓글 수정 성공", 200);
    }

    @Override
    @Transactional
    public ApiResponseDto deleteCommunityComment(User user, Long commentId) {
        CommunityComment comment = findCommunityComment(commentId);
        communityCommentUserVerification(comment, user);

        communityCommentRepository.delete(comment);
        return new ApiResponseDto("댓글 삭제 성공", 200);
    }

    public CommunityComment findCommunityComment(Long id) {
        return communityCommentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }

    private void communityCommentUserVerification(CommunityComment comment, User user) {
        if(!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("댓글을 작성한 유저가 아닙니다");
        }
    }
}
