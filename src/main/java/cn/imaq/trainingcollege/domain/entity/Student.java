package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;

    private String email;

    private String pwdHash;

    private String name;

    private Status status;

    public enum Status {
        NOT_VERIFIED,
        VERIFIED,
        TERMINATED
    }
}
