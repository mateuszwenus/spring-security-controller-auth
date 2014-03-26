package com.github.mateuszwenus.spring_security_controller_auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class NegativeTest extends TestSupport {

  private HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create();

  @Test
  public void shouldThrowExceptionForHasRole() throws Exception {
    // given
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("hasRole('ROLE_ADMIN')"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }

  @Test
  public void shouldThrowExceptionForHasAnyRole() throws Exception {
    // given
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }

  @Test
  public void shouldAllowDirectAccessToPrincipal() throws Exception {
    // given
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("principal == 'admin'"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }

  @Test
  public void shouldAllowDirectAccessToAuthentication() throws Exception {
    // given
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("authentication.principal == 'admin'"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }

  @Test
  public void shouldThrowExceptionForDenyAll() throws Exception {
    // given
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("denyAll()"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }
}
