package com.github.mateuszwenus.spring_security_controller_auth;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

public class WebExpressionVoterAdapter implements AccessDecisionVoter<HandlerInvocation> {

  private final WebExpressionVoter webExpressionVoter;

  public WebExpressionVoterAdapter(WebExpressionVoter webExpressionVoter) {
    this.webExpressionVoter = webExpressionVoter;
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return webExpressionVoter.supports(attribute);
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return HandlerInvocation.class.isAssignableFrom(clazz);
  }

  @Override
  public int vote(Authentication authentication, HandlerInvocation object, Collection<ConfigAttribute> attributes) {
    FilterInvocation fi = new FilterInvocation(object.getRequest(), object.getResponse(), new NullFilterChain());
    return webExpressionVoter.vote(authentication, fi, attributes);
  }
}
