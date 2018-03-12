package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class College {
    private Integer id;

    private String pwdHash;

    private Integer profileId;

    private Integer pendingProfileId;
}
