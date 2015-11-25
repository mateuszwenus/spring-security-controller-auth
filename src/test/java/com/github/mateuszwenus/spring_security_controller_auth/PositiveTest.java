package com.github.mateuszwenus.spring_security_controller_auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class PositiveTest extends TestSupport {

	@Autowired
	private ApplicationContext ctx;
	
  @Test
  public void shouldSucceedForHasRole() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    // when
    boolean result = interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("hasRole('ROLE_USER')"));
    // then
    assertThat(result, is(true));
  }

  @Test
  public void shouldSucceedForHasAnyRole() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    // when
    boolean result = interceptor.preHandle(mockHttpRequest(), mockHttpResponse(),
        createHandlerMethod("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')"));
    // then
    assertThat(result, is(true));
  }

  @Test
  public void shouldAllowDirectAccessToPrincipal() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    // when
    boolean result = interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("principal == 'user'"));
    // then
    assertThat(result, is(true));
  }

  @Test
  public void shouldAllowDirectAccessToAuthentication() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    // when
    boolean result = interceptor
        .preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("authentication.principal == 'user'"));
    // then
    assertThat(result, is(true));
  }

  @Test
  public void shouldSucceedForPermitAll() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    // when
    boolean result = interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("permitAll()"));
    // then
    assertThat(result, is(true));
  }

  @Test
  public void shouldSucceedWhenIpAddressMatch() throws Exception {
    // given
  	HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
    SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
    // when
    boolean result = interceptor.preHandle(mockHttpRequest("127.0.0.1"), mockHttpResponse(),
        createHandlerMethod("hasIpAddress('127.0.0.1')"));
    // then
    assertThat(result, is(true));
  }
  
	@Test
	public void shouldSucceedForServiceThatReturnsTrue() throws Exception {
		// given
		HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create(ctx);
		SecurityContextHolder.getContext().setAuthentication(createAuthentication("user", "ROLE_USER"));
		// when
		boolean result = interceptor.preHandle(mockHttpRequest(), mockHttpResponse(), createHandlerMethod("@fooService.returnTrue()"));
		// then
		assertThat(result, is(true));
	}
}
