package com.example.livingbymyselfserver.community.repository;

import com.example.livingbymyselfserver.community.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryQuery {
    List<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
