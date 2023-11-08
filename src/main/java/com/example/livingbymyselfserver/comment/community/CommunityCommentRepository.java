package com.example.livingbymyselfserver.comment.community;

import com.example.livingbymyselfserver.comment.entity.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
}
