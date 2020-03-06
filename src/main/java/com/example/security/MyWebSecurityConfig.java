package com.example.security;

import com.example.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */

@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    CustomAccessDecisionManager customADManager;
    @Autowired
    CustomFileterInvocationSecurityMetadaSourece customFISMSourece;

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* auth.inMemoryAuthentication()
                .withUser("admin").password("123").roles("ADMIN", "USER")
                .and()
                .withUser("sang").password("123").roles("USER")
               ;*/
        auth.userDetailsService(userService);
    }
    @Override
    protected void configure (HttpSecurity http) throws Exception{
        http.authorizeRequests()
               /**
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**")
                .access("hasAnyRole('admin','user')")
                .anyRequest()
                .authenticated()
                */
               .withObjectPostProcessor(
                       new ObjectPostProcessor<FilterSecurityInterceptor>() {
                           @Override
                           public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                               object.setSecurityMetadataSource(customFISMSourece);//将动态获取到的权限加进这里
                               object.setAccessDecisionManager(customADManager);
                               return object;
                           }
                       }
               )


                .and()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .and()
                .csrf()
                .disable()
        ;
    }
    /**
     * 动态权限设置
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_db > ROLE_admin > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


}
