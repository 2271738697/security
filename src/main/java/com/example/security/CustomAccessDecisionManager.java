package com.example.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 当一个请求走完 FilterlnvocationSecurityMetadataSource 中的 getAttributes 方法后，接下来就会
 * AccessDecisionManager 类中进行角色信息的 比对，自定义 AccessDecisionManager 如下：
 *
 * @author zhoudb
 * @date 2019/12/23 16:59
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * * 该方法中判断当前登录的用户是否具
     * 备当前请求 URL 所需要的角色信息，如果不具备，就抛出 AccessDeniedException 异常，否
     * 则不做任何事即可
     * * 第一个参数 包含当前登录用户的信息；第二个参数则是一个
     * Filterlnvocation，可以取当前请求对象等；第三个参数就是
     * FilterlnvocationSecurityMetadataSource 中的 getAttributes 方法的返回值
     * 即当前请求 URL需要的角色。
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> auths = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : collection){
            if ("ROLE_LOGIN".equals(configAttribute.getAttribute()) && authentication instanceof UsernamePasswordAuthenticationToken){
                return;
            }
            for (GrantedAuthority authority : auths) {
                // 相等则说明已经登录！
                if (configAttribute.getAttribute().equals(authority.getAuthority())) {
                    return;
                }
            }

        }
        throw new AccessDeniedException("权限不足");

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
