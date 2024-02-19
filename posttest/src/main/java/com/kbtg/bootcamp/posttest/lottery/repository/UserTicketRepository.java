package com.kbtg.bootcamp.posttest.lottery.repository;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket,Integer> {


    @Query(value = "SELECT new "
        + "com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary(" +
                "u.ticket, u.userId,  SUM(u.amount), sum(u.totalBill) " +
            ") " +
            "from " +
                " UserTicket u "
        + " where u.userId = :userId GROUP BY u.ticket , u.userId")
    List<UserTicketSummary> findSumPriceAmountByUserId(String userId);

    @Query(value = "SELECT new "
            + "com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketSummary(" +
                "u.ticket, u.userId,  SUM(u.amount), sum(u.totalBill) " +
            ") " +
            "from " +
            " UserTicket u "
            + " where " +
            "u.userId = :userId " +
            "and u.ticket =:ticketId " +
            "GROUP BY u.ticket , u.userId")
    Optional<UserTicketSummary> findSumPriceAmountByUserIdTicketId(String userId,String ticketId);

    @Modifying
    @Query("DELETE from UserTicket u WHERE u.userId=:userId and u.ticket=:ticketId")
    void deleteUserTicketByUserIdTicket(@Param("userId") String userId, @Param("ticketId")String ticketId);

}
