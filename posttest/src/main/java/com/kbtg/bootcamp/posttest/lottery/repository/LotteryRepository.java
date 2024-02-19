package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryRepository extends CrudRepository<Lottery,String> {
    @Override
    Optional<Lottery> findById(String s);

    @Query(value = "SELECT ticket FROM lottery", nativeQuery = true)
    List<String> getAllTicket();



}
