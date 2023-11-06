package com.example.livingbymyselfserver.fairs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FairRepository extends JpaRepository<Fair, Long> {

}
