package com.github.mateuszwenus.spring_security_controller_auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HandlerSecurityInterceptor extends AbstractSecurityInterceptor implements HandlerInterceptor {

  private ThreadLocal<InterceptorStatusToken> tokenTL = new ThreadLocal<InterceptorStatusToken>();

  private HandlerInvocationSecurityMetadataSource securityMetadataSource;

  public void setSecurityMetadataSource(HandlerInvocationSecurityMetadataSource securityMetadataSource) {
    this.securityMetadataSource = securityMetadataSource;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
    if (handler instanceof HandlerMethod){
      HandlerInvocation hi = new HandlerInvocation(req, resp, (HandlerMethod)handler);
      InterceptorStatusToken token = super.beforeInvocation(hi);
      tokenTL.set(token);
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception exc) throws Exception {
    InterceptorStatusToken token = tokenTL.get();
    if (token != null) {
      tokenTL.set(null);
      super.finallyInvocation(token);
      super.afterInvocation(token, null);
    }
  }

  @Override
  public Class<?> getSecureObjectClass() {
    return HandlerInvocation.class;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return securityMetadataSource;
  }
}
