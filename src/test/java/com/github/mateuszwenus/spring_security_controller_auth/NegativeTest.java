package com.github.mateuszwenus.spring_security_controller_auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class NegativeTest extends TestSupport {

	@Autowired
	private ApplicationContext ctx;
	
  @Test
  public void shouldThrowExceptionForHasRole() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
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
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
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
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
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
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
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
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("denyAll()"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }

  @Test
  public void shouldThrowExceptionWhenIpAddressDoesNotMatch() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    try {
      // when
      interceptor.preHandle(mockHttpRequest("127.0.0.2"), mockHttpResponse(), createHandlerMethod("hasIpAddress('127.0.0.1')"));
      Assert.fail();
    } catch (AccessDeniedException expected) {
      // then
    }
  }
  
	@Test
	public void shouldThrowExceptionWhenServiceReturnsFalse() throws Exception {
		// given
		HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
		SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
		try {
			// when
			interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("@fooService.returnFalse()"));
			Assert.fail();
		} catch (AccessDeniedException expected) {
			// then
		}
	}
}
