Spring-security-controller-auth is a library, which allows you to apply authorization rules to controller methods using annotations without moving `<global-method-security>` or `@EnableGlobalMethodSecurity` to web application context. It provides a solution, which is something between web security and global method security. It operates on HTTP request level (just like web security), but it is configured by annotations (like global method security).

## Installation
The library is not in any maven repository, so you have to install it manually to your local repo:
```bash
git clone https://github.com/mateuszwenus/spring-security-controller-auth.git
cd spring-security-controller-auth
git checkout 0.9.0
mvn javadoc:jar source:jar install
```
## Setup

After installation add the following dependency to your pom.xml:
```xml
<dependency>
  <groupId>com.github.mateuszwenus</groupId>
  <artifactId>spring-security-controller-auth</artifactId>
  <version>0.9.0</version>
</dependency>
```
Then you have to make some configuration. The central class in this library is `HandlerSecurityInterceptor`. It is a Spring MVC interceptor, which handles authrorization annotations. To use spring-security-controller-auth in your project you have to create and configure this interceptor. The simplest config looks like this:
```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() {
    try {
      return super.authenticationManagerBean();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Bean
  public HandlerSecurityInterceptor handlerSecurityInterceptor() {
    HandlerSecurityInterceptor interceptor = HandlerSecurityInterceptor.create();
    interceptor.setAuthenticationManager(authenticationManagerBean());
    return interceptor;
  }

  ...
}

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(webSecurityConfig.handlerSecurityInterceptor());
  }

  ...
}
```
The only not-so-obvious thing here is the overriden `authenticationManagerBean()` method. It is necessary because `HandlerSecurityInterceptor` requires an `AuthenticationManager` and by default `WebSecurityConfigurerAdapter` doesn't expose it as a bean (see javadoc for `WebSecurityConfigurerAdapter.authenticationManagerBean()` for more information).

After configuration, you can easily use it in your controllers, for example:
```java
@AuthorizeRequest("hasRole('ROLE_USER')")
@RequestMapping("/index")
public String index() {
  ...
}

@AuthorizeRequest("hasRole('ROLE_ADMIN') and hasIpAddress('127.0.0.1')")
@RequestMapping("/admin")
public String admin() {
  ...
}
```
The `AuthorizeRequest` annotation accepts all web security expressions.

A more complex configuration and usage example can be found in my [spring4-webbapp](https://github.com/mateuszwenus/spring4-webapp) project. It shows how to configure this library with a `RoleHierarchy`. You can also read more about spring-security-controller-auth [on my blog](http://mwenus.blogspot.com/2014/03/spring-security-authorization-on.html).
