package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollegeProfileDto {
    private Integer id;

    private CollegeProfile current;

    private CollegeProfile pending;
}
