package com.kbtg.bootcamp.posttest.lottery.service;


import com.kbtg.bootcamp.posttest.lottery.model.response.LotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;


    public LotteryResponse createlLottery(){
        return null;
    }


    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }


    public LotteryResponse getLottery(){
        return null;
    }

    public LotteryResponse purchaseLottery(){
        return null;
    }


    public LotteryResponse cancelLotteryPerchase(){
        return null;
    }



}
