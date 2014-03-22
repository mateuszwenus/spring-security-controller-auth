package com.github.mateuszwenus.spring_security_controller_auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

public class HandlerInvocation {

  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final HandlerMethod method;

  public HandlerInvocation(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) {
    this.request = request;
    this.response = response;
    this.method = method;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public HandlerMethod getMethod() {
    return method;
  }
}
