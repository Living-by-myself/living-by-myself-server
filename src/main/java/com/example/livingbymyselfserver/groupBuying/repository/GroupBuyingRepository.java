package com.example.livingbymyselfserver.groupBuying.repository;

import com.example.livingbymyselfserver.community.Community;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupBuyingRepository extends JpaRepository<GroupBuying, Long>, GroupBuyingRepositoryQuery {
  List<GroupBuying> findAllByHost(User user);

}
