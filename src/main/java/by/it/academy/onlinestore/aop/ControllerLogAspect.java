package by.it.academy.onlinestore.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class ControllerLogAspect extends BaseAspect {
    @Pointcut("execution(* by.it.academy.onlinestore.controllers..*(..)) " +
            "&& !@annotation(by.it.academy.onlinestore.aop.ExcludeLog))")
    public void isControllerLayer() {
    }

    @Before("isControllerLayer()")
    public void logControllersBefore(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(BEFORE_CONTROLLER_PATTERN,
                request.getMethod(),
                joinPoint.getSignature().toShortString(),
                request.getRequestURI(),
                getArgsWithName(joinPoint));
    }

    @AfterReturning(pointcut = "isControllerLayer()", returning = "result")
    public void logControllerAfter(JoinPoint joinPoint, Object result) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(AFTER_CONTROLLER_PATTERN,
                request.getMethod(),
                joinPoint.getSignature().toShortString(),
                request.getRequestURI(),
                getStringInstanceOf(Optional.ofNullable(result).orElse("")),
                getArgsWithName(joinPoint));
    }
}
