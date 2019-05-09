package com.le.sso.expression;

import com.le.core.util.Constant;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-05-09 14:28
 **/
@Component
public class SupperPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) {
            return false;
        }

        return Constant.SUPER_ADMIN.equals(authentication.getPrincipal())
                || authentication.getAuthorities().contains(permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
