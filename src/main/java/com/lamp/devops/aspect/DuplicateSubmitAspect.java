package com.lamp.devops.aspect;

import cn.hutool.core.util.StrUtil;
import com.lamp.devops.annotation.PreventDuplicateSubmit;
import com.lamp.devops.exception.BusinessException;
import com.lamp.devops.fastmap.IFastMap;
import com.lamp.devops.lang.RespCode;
import com.lamp.devops.utils.TokenProviderUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 处理重复提交的切面
 */
@Slf4j
@Aspect
@Component
public class DuplicateSubmitAspect {
    private static final String RESUBMIT_LOCK_PREFIX = "LOCK:RESUBMIT:";

    @Resource
    private IFastMap<String, String> fastMap;

    /**
     * 防重复提交切点
     */
    @Pointcut("@annotation(preventDuplicateSubmit)")
    public void preventDuplicateSubmitPointCut(PreventDuplicateSubmit preventDuplicateSubmit) {
    }

    @Around(value = "preventDuplicateSubmitPointCut(preventDuplicateSubmit)", argNames = "pjp,preventDuplicateSubmit")
    public Object doAround(ProceedingJoinPoint pjp, PreventDuplicateSubmit preventDuplicateSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = TokenProviderUtil.resolve(request);
        if (StrUtil.isNotBlank(token)) {
            String username = TokenProviderUtil.getAccount(token);
            String resubmitLockKey = RESUBMIT_LOCK_PREFIX + username + ":" + request.getMethod() + "-" + request.getServletPath();
            fastMap.put(resubmitLockKey, "1");

            long expire = preventDuplicateSubmit.expire(); // 防重提交锁过期时间
            fastMap.expire(resubmitLockKey, expire, (key, val) -> fastMap.remove(resubmitLockKey));

            String lockKey = fastMap.get(resubmitLockKey);
            if (StrUtil.isNotBlank(lockKey)) {
                throw new BusinessException(RespCode.REPEAT_SUBMIT_ERROR); // 抛出重复提交提示信息
            }
        }
        return pjp.proceed();
    }
}

