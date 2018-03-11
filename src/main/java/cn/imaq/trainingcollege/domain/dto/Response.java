package cn.imaq.trainingcollege.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {
    private boolean success;

    private T object;

    private String msg;

    public static <T> Response<T> ofSuccess() {
        return new Response<>(true, null, null);
    }

    public static <T> Response<T> ofSuccess(T object) {
        return new Response<>(true, object, null);
    }

    public static <T> Response<T> ofFailure(String msg) {
        return new Response<>(true, null, msg);
    }
}
