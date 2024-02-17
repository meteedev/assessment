package com.kbtg.bootcamp.posttest.lottery.repository;

import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery,Integer> {

}
