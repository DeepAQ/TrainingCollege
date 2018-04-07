package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.CollegeSettlementDto;
import cn.imaq.trainingcollege.domain.entity.Order;
import cn.imaq.trainingcollege.domain.entity.Settlement;
import cn.imaq.trainingcollege.mapper.CollegeMapper;
import cn.imaq.trainingcollege.mapper.CollegeProfileMapper;
import cn.imaq.trainingcollege.mapper.OrderMapper;
import cn.imaq.trainingcollege.mapper.SettlementMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SettlementService {
    @Autumnwired
    private SettlementMapper settlementMapper;

    @Autumnwired
    private CollegeMapper collegeMapper;

    @Autumnwired
    private CollegeProfileMapper collegeProfileMapper;

    @Autumnwired
    private OrderMapper orderMapper;

    public List<CollegeSettlementDto> getCollegeSettlements() {
        return collegeMapper.getAll().stream().map(c -> {
            String name = collegeProfileMapper.getById(c.getProfileId()).getName();
            return CollegeSettlementDto.builder()
                    .collegeId(c.getId())
                    .collegeName(name)
                    .totalIncome(orderMapper.sumByCollegeId(c.getId(), Order.Status.PAID))
                    .settledIncome(settlementMapper.sumOrigByCollegeId(c.getId()))
                    .build();
        }).collect(Collectors.toList());
    }

    public void addSettlement(Integer collegeId, Integer origAmount, Integer ratio) {
        settlementMapper.insert(
                Settlement.builder()
                        .collegeId(collegeId)
                        .origAmount(origAmount)
                        .realAmount(origAmount * ratio / 10)
                        .time((int) (System.currentTimeMillis() / 1000))
                        .build()
        );
    }
}
