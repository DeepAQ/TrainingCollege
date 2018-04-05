package cn.imaq.trainingcollege.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeInfoDto {
    private Integer id;

    private String name;

    private Integer profileId;

    private Integer pendingProfileId;
}
