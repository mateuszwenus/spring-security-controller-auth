package com.github.mateuszwenus.spring_security_controller_auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.method.HandlerMethod;

public abstract class TestSupport extends AbstractJUnit4SpringContextTests {

  protected Authentication createAuthentication(String user, String... roles) {
    return new TestingAuthenticationToken(user, "pass", roles);
  }

  protected HttpServletRequest mockHttpRequest() {
    return new MockHttpServletRequest();
  }

  protected HttpServletRequest mockHttpRequest(String ipAddress) {
    MockHttpServletRequest req = new MockHttpServletRequest();
    req.setRemoteAddr(ipAddress);
    return req;
  }

  protected HttpServletResponse mockHttpResponse() {
    return new MockHttpServletResponse();
  }

  protected Object createHandlerMethod(String expr) {
    AuthorizeRequest ann = mock(AuthorizeRequest.class);
    when(ann.value()).thenReturn(expr);
    HandlerMethod handlerMethod = mock(HandlerMethod.class);
    when(handlerMethod.getMethodAnnotation(AuthorizeRequest.class)).thenReturn(ann);
    return handlerMethod;
  }
}
