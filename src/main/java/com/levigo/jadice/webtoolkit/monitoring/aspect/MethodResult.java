package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.Base;

@Aspect
public class MethodResult extends Base {

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedResult)")
  protected void pointcut() {
  }

  @Override
  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedDuration id) throws Throwable {

    Object methodResult = super.determineMetricName(joinPoint, id);

    super.publish(getClass(), methodResult);

    return methodResult;
  }
}