package cn.imaq.trainingcollege.support.aspect;

import cn.imaq.autumn.rest.annotation.ControllerAdvice;
import cn.imaq.autumn.rest.annotation.ExceptionHandler;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.support.exception.ServiceException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    public Response handle(Throwable t) {
        if (t instanceof ServiceException) {
            return Response.ofFailure(t.getMessage());
        }
        return Response.ofFailure("服务器错误: " + t.getMessage());
    }
}
