package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedResult;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.ReturnData;

@Aspect
public class MethodResult {

  private String label = "";

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedResult)")
  protected void pointcut() {
  }

  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedResult ir) throws Throwable {

    Object methodResult = joinPoint.proceed();
    Publisher.pushToAdapter(new ReturnData(ir.value(), label, methodResult));

    return methodResult;
  }
}