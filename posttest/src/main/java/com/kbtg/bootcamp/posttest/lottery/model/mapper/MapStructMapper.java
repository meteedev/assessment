package com.kbtg.bootcamp.posttest.lottery.model.mapper;


import com.kbtg.bootcamp.posttest.lottery.model.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.lottery.model.dto.UserTicketDto;
import com.kbtg.bootcamp.posttest.lottery.model.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.model.entity.UserTicket;
import com.kbtg.bootcamp.posttest.lottery.model.request.CreateRequest;
import com.kbtg.bootcamp.posttest.lottery.model.response.CreateLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.model.response.PurchaseLotteryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MapStructMapper {
    LotteryDto mapCreateRequestToDTO(CreateRequest createRequest);
    Lottery mapLotteryDTOToEntity(LotteryDto lotteryDto);
    CreateLotteryResponse mapLotteryEntityToCreateLotteryResponse(Lottery lottery);

    @Mapping(target = "no", ignore = true)
    @Mapping(target = "totalBill", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "amount", ignore = true)
    UserTicket mapUserTicketDTOToUserTicket(UserTicketDto userTicketDto);
    @Mapping(target = "id", source = "no")
    PurchaseLotteryResponse mapUserTicketToPurchaseLotteryResponse(UserTicket userTicket);

}
