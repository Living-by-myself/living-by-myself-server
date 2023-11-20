package com.example.livingbymyselfserver.community.repository;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.community.dto.CommunityListResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityResponseDto;
import com.example.livingbymyselfserver.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryQuery {
    List<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Community> findAllByUser(User user);

    int countAllByUser(User user);
}
