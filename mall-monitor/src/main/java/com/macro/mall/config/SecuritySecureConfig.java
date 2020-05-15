package com.macro.mall.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by macro on 2019/9/30.
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests()
                //1.配置所有静态资源和登录页可以公开访问
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                //2.配置登录和登出路径
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                //3.开启http basic支持，admin-client注册时需要使用
                .httpBasic().and()
                .csrf()
                //4.开启基于cookie的csrf保护
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //5.忽略这些路径的csrf保护以便admin-client注册
                .ignoringAntMatchers(
                        adminContextPath + "/instances",
                        adminContextPath + "/actuator/**"
                );

        //定制退出
        http.logout()
                //.logoutUrl("/sys/doLogout")  //只支持定制退出url
                //支持定制退出url以及httpmethod
                .logoutRequestMatcher(new AntPathRequestMatcher("/sso/logout", "GET"))
                .addLogoutHandler((request,response,authentication) -> System.out.println("=====1====="))
                .addLogoutHandler((request,response,authentication) -> System.out.println("=====2======"))
                .addLogoutHandler((request,response,authentication) -> System.out.println("=====3======"))
                .logoutSuccessHandler(((request, response, authentication) -> {
                    System.out.println("=====4=======");
                    response.sendRedirect("/html/logoutsuccess1.html");
                }))
                //.logoutSuccessUrl("/html/logoutsuccess2.html")  //成功退出的时候跳转的页面
                //.deleteCookies()  //底层也是使用Handler实现的额
                //清除认证信息
                .clearAuthentication(true)
                .invalidateHttpSession(true)
        ;
    }
}
