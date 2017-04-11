package com.levigo.jadice.webtoolkit.monitoring.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jadice.web.util.instrumented.metrics.InstrumentedResult;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.ReturnData;

@Aspect
public class MethodResult {

  private String metricLabel = "";

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedResult)")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    InstrumentedResult ir = method.getAnnotation(InstrumentedResult.class);

    Object methodResult = joinPoint.proceed();

    Publisher.pushToAdapter(new ReturnData(ir.name(), ir.description(), metricLabel, methodResult));

    return methodResult;
  }
}