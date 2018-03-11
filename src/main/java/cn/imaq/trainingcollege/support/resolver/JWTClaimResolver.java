package cn.imaq.trainingcollege.support.resolver;

import cn.imaq.autumn.rest.param.resolver.AnnotatedParamResolver;
import cn.imaq.autumn.rest.param.value.ParamValue;
import cn.imaq.autumn.rest.param.value.SingleValue;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;
import cn.imaq.trainingcollege.util.JWTUtil;
import cn.imaq.trainingcollege.util.Sensitive;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class JWTClaimResolver extends AnnotatedParamResolver<JWTClaim> {
    @Override
    protected ParamValue resolve(Parameter param, JWTClaim anno, HttpServletRequest request, HttpServletResponse response) {
        String jwt = request.getHeader(Sensitive.JWT_HEADER);
        if (StringUtils.isBlank(jwt)) {
            return new SingleValue<>(null);
        }
        try {
            return new SingleValue<>(JWTUtil.parse(jwt, param.getParameterizedType()));
        } catch (Exception e) {
            return new SingleValue<>(null);
        }
    }
}
