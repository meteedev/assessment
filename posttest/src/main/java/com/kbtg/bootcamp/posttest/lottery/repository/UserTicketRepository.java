package com.kbtg.bootcamp.posttest.lottery.repository;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket,Integer> {
    Optional<List<UserTicket>> findByUserId(String userId);

}
