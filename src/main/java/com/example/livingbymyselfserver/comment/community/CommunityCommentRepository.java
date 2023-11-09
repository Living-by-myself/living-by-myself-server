package com.example.livingbymyselfserver.comment.community;

import com.example.livingbymyselfserver.comment.entity.CommunityComment;
import com.example.livingbymyselfserver.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    List<CommunityComment> findByCommunityOrderByCreatedAtDesc(Community community, Pageable pageable);
}
