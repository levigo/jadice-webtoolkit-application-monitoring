package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.InstrumentedResult;
import com.levigo.jadice.webtoolkit.monitoring.data.ReturnData;

/**
 * This aspect handles all methods annotated by {@link InstrumentedResult}.
 */
@Aspect
public class MethodResult extends BasicAspect {


  @Override
  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.InstrumentedResult)")
  public void pointcut() {
  }

  /**
   * Determines the metric information. This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    Object methodResult = joinPoint.proceed();
    ReturnData data = new ReturnData(methodResult);

    super.determineMetricInformation(data, joinPoint, InstrumentedResult.class);
    super.publish(data);

    return methodResult;
  }
}