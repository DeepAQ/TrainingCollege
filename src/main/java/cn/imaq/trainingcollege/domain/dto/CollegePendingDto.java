package cn.imaq.trainingcollege.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegePendingDto {
    private List<CollegeInfoDto> newProfiles;

    private List<CollegeInfoDto> editProfiles;
}
