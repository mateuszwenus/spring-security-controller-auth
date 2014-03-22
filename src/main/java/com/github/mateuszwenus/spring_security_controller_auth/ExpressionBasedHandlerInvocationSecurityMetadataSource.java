package com.github.mateuszwenus.spring_security_controller_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.PublicWebExpressionConfigAttribute;
import org.springframework.web.method.HandlerMethod;

public class ExpressionBasedHandlerInvocationSecurityMetadataSource implements HandlerInvocationSecurityMetadataSource {

  private SecurityExpressionHandler<?> expressionHandler = new DefaultWebSecurityExpressionHandler();

  public void setExpressionHandler(SecurityExpressionHandler<?> expressionHandler) {
    this.expressionHandler = expressionHandler;
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    HandlerInvocation invocation = (HandlerInvocation) object;
    HandlerMethod method = invocation.getMethod();
    AuthorizeRequest ann = method.getMethodAnnotation(AuthorizeRequest.class);
    if (ann != null) {
      Expression expr = expressionHandler.getExpressionParser().parseExpression(ann.value());
      ConfigAttribute attr = new PublicWebExpressionConfigAttribute(expr);
      return Arrays.asList(attr);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return HandlerInvocation.class.isAssignableFrom(clazz);
  }
}
