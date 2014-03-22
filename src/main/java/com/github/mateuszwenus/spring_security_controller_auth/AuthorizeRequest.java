package com.github.mateuszwenus.spring_security_controller_auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizeRequest {
  /**
   * @return the Spring-EL expression to be evaluated before invoking the protected method
   */
  String value();
}
