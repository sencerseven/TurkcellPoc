package com.turkcell.poc.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.poc.model.RecordInputDto;
import com.turkcell.poc.service.RecordService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(1)
public class HttpAspect {

  @Autowired
  RecordService recordService;

  @Pointcut("execution(* com.turkcell.poc.controller.*.*(..))")
  public void action(){

  }

  /**
   * We interrupt incoming requests with aspect before processing so we can log
   * @param joinPoint
   */
  @SneakyThrows
  @Before("action()")
  public void logBefore(JoinPoint joinPoint) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    RecordInputDto recordInputDto = new RecordInputDto();
    recordInputDto.setCreateDate(new Date());
    recordInputDto.setUri(request.getRequestURI());
    recordInputDto.setOwner(authentication.getName());
    recordInputDto.setRequestType(request.getMethod());

    ObjectMapper objectMapper = new ObjectMapper();
    recordInputDto.setBody(objectMapper.writeValueAsString(joinPoint.getArgs()));

    recordService.addRecord(recordInputDto);

  }


}
